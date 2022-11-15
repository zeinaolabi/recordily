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
use Illuminate\Support\Facades\File;

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
            ->orderBy('created_at', 'DESC')
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

    public function getPlaylistSongs(int $playlistID): JsonResponse
    {
        $songs = PlaylistHasSong::where('playlist_id', $playlistID)->pluck('song_id');

        $result = Song::FetchSongs($songs);

        return response()->json($result);
    }

    public function getPlaylistInfo(int $playlistID): JsonResponse
    {
        $playlist = Playlist::find($playlistID);

        if (!$playlist) {
            return response()->json("Playlist not found", 204);
        }

        $playlist->picture = $this->urlGenerator->to($playlist->picture);

        return response()->json($playlist);
    }

    public function addPlaylist(PlaylistRequest $request): JsonResponse
    {
        $id = $this->authManager->guard()->id();

        $path = public_path() . '/images/' . $id;

        if (!File::exists($path)) {
            File::makeDirectory($path);
        }

        try {
            $picture = $request->file('picture');

            $picturePath = '/images/' . $id . '/' . uniqid() . '.' . $picture->extension();
            file_put_contents(public_path() . $picturePath, $picture->getContent());
        } catch (Exception $e) {
            return response()->json(['error' => $e], 400);
        }

        $playlistName = str_replace('"', '', $request->get('name'));

        if (!Playlist::createPlaylist($id, $playlistName, $picturePath)) {
            return response()->json('unsuccessfully attempt', 400);
        }

        return response()->json('successfully created', 201);
    }

    public function editPlaylist(EditPlaylistRequest $request): JsonResponse
    {
        $id = $this->authManager->guard()->id();

        $playlist = Playlist::find(str_replace('"', '', $request->get('playlist_id')));

        if ($request->file('picture')) {
            try {
                $picture = $request->file('picture');

                $picturePath = '/images/' . $id . '/' . uniqid() . '.' . $picture->extension();
                file_put_contents(public_path() . $picturePath, $picture->getContent());

                $playlist->picture = $picturePath;
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

    public function deletePlaylist(int $playlistID): JsonResponse
    {
        $playlist = Playlist::find($playlistID);

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
        $id = $this->authManager->guard()->id();

        $search_playlist = Playlist::searchPlaylist($id, $input);

        return response()->json($search_playlist);
    }

    public function addToPlaylist(int $playlistID, int $songID): JsonResponse
    {
        if (!Playlist::exists($playlistID)) {
            return response()->json('Playlist Not Found', 400);
        }

        if (PlaylistHasSong::songInPlaylist($playlistID, $songID)) {
            return response()->json('Song in playlist', 400);
        }

        PlaylistHasSong::addToPlaylist($playlistID, $songID);

        return response()->json('Successfully Added', 201);
    }

    public function removeFromPlaylist(int $playlistID, int $songID): JsonResponse
    {
        if (!Playlist::exists($playlistID)) {
            return response()->json('Playlist Not Found', 400);
        }

        if (!PlaylistHasSong::songInPlaylist($playlistID, $songID)) {
            return response()->json('Song in playlist', 400);
        }

        PlaylistHasSong::removeFromPlaylist($playlistID, $songID);

        return response()->json('Successfully Removed', 201);
    }
}
