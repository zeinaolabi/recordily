<?php

namespace App\Http\Controllers;

use App\Http\Requests\EditPlaylistRequest;
use App\Http\Requests\PlaylistRequest;
use App\Models\Playlist;
use App\Models\PlaylistHasSong;
use App\Models\Song;
use Exception;
use Illuminate\Contracts\Auth\Factory;
use Illuminate\Contracts\Routing\UrlGenerator;
use Illuminate\Http\JsonResponse;
use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\File;
use Illuminate\Support\Facades\URL;

class PlaylistController extends Controller
{
    public function __construct(
        private readonly Factory $authManager,
        private readonly UrlGenerator $urlGenerator
    ) {
    }

    public function getPlaylists(): JsonResponse
    {
        $playlists = Playlist::where('user_id', $this->authManager->guard()->id())
            ->get()
            ->each(fn(Playlist $playlist) => $playlist->picture = $this->urlGenerator->to($playlist->picture));

        return response()->json($playlists);
    }

    public function getLimitedPlaylists(int $limit): JsonResponse
    {
        $playlists = Playlist::where('user_id', $this->authManager->guard()->id())
            ->limit($limit)
            ->get()
            ->each(fn(Playlist $playlist) => $playlist->picture = $this->urlGenerator->to($playlist->picture));

        return response()->json($playlists);
    }

    public function getPlaylistSongs(int $playlist_id): JsonResponse
    {
        $songs = PlaylistHasSong::where('playlist_id', $playlist_id)->pluck('song_id');

        $result = Song::FetchSongs($songs);

        return response()->json($result);
    }

    public function getPlaylistInfo(int $playlist_id): JsonResponse
    {
        $playlist = Playlist::find($playlist_id);

        if (!$playlist) {
            return response()->json("Playlist not found", 204);
        }

        $playlist->picture = URL::to($playlist->picture);

        return response()->json($playlist);
    }

    public function addPlaylist(PlaylistRequest $request): JsonResponse
    {
        $id = Auth::id();

        $path = public_path() . '/images/' . $id;

        if (!File::exists($path)) {
            File::makeDirectory($path);
        }

        try {
            $picture = $request->file('picture');

            $picture_path = '/images/' . $id . '/' . uniqid() . '.' . $picture->extension();
            file_put_contents(public_path() . $picture_path, $picture->getContent());
        } catch (Exception $e) {
            return response()->json(['error' => $e], 400);
        }

        $playlist_name = str_replace('"', '', $request->get('name'));

        if (!Playlist::createPlaylist($id, $playlist_name, $picture_path)) {
            return response()->json('unsuccessfully attempt', 400);
        }

        return response()->json('successfully created', 201);
    }

    public function editPlaylist(EditPlaylistRequest $request): JsonResponse
    {
        $id = Auth::id();

        $playlist = Playlist::find(str_replace('"', '', $request->get('playlist_id')));

        if ($request->file('picture')) {
            try {
                $picture = $request->file('picture');

                $picture_path = '/images/' . $id . '/' . uniqid() . '.' . $picture->extension();
                file_put_contents(public_path() . $picture_path, $picture->getContent());

                $playlist->picture = $picture_path;
            } catch (Exception $e) {
                return response()->json(['error' => $e], 400);
            }
        }

        $playlist->name = $request->get('name') ? str_replace('"', '', $request->get('name')) : $playlist->name;

        if (!$playlist->save()) {
            return response()->json('unsuccessfully attempt', 400);
        }

        return response()->json('successfully edited', 201);
    }

    public function deletePlaylist(int $playlistId): JsonResponse
    {
        $playlist = Playlist::find($playlistId);

        if (! $playlist instanceof Playlist) {
            return response()->json('Playlist not found', 404);
        }

        if ($playlist->delete() !== true) {
            return response()->json('Unsuccessful delete attempt', 400);
        }

        return response()->json('Successfully deleted', 200);
    }

    public function searchPlaylists(string $input): JsonResponse
    {
        $id = Auth::id();

        $search_playlist = Playlist::searchPlaylist($id, $input);

        return response()->json($search_playlist);
    }

    public function addToPlaylist(int $playlist_id, int $song_id): JsonResponse
    {
        if (!Playlist::exists($song_id)) {
            return response()->json('Playlist Not Found', 400);
        }

        if (Playlist::songInPlaylist($playlist_id, $song_id)) {
            return response()->json('Song in playlist', 400);
        }

        Playlist::addToPlaylist($playlist_id, $song_id);

        return response()->json('Successfully Added', 201);
    }

    public function removeFromPlaylist( int $playlist_id, int $song_id): JsonResponse
    {
        if (!Playlist::exists($playlist_id)) {
            return response()->json('Playlist Not Found', 400);
        }

        if (!Playlist::songInPlaylist($playlist_id, $song_id)) {
            return response()->json('Song in playlist', 400);
        }

        Playlist::removeFromPlaylist($playlist_id, $song_id);

        return response()->json('Successfully Removed', 201);
    }
}
