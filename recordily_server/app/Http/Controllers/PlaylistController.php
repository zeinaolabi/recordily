<?php

namespace App\Http\Controllers;

use App\Http\Requests\EditPlaylistRequest;
use App\Http\Requests\createPlaylistRequest;
use App\Http\Requests\PlaylistRequest;
use App\Models\Playlist;
use App\Models\PlaylistHasSong;
use App\Models\Song;
use Illuminate\Contracts\Auth\Factory;
use Illuminate\Contracts\Routing\UrlGenerator;
use Illuminate\Filesystem\Filesystem;
use Illuminate\Filesystem\FilesystemManager;
use Illuminate\Http\JsonResponse;

class PlaylistController extends Controller
{
    use InfoTrait;

    public function __construct(
        private readonly Factory $authManager,
        private readonly UrlGenerator $urlGenerator,
        private readonly Filesystem $filesystem,
        private readonly FilesystemManager $filesystemManager
    ) {
    }

    public function getPlaylists(): JsonResponse
    {
        $id = $this->authManager->guard()->id();

        $playlists = Playlist::getPlaylists($id)
            ->each(fn(Playlist $playlist) => $playlist->picture = $this->urlGenerator->to($playlist->picture));

        return response()->json($playlists);
    }

    public function getLimitedPlaylists(int $limit): JsonResponse
    {
        $playlists = Playlist::where('user_id', $this->authManager->guard()->id())->limit($limit)->get()
            ->each(fn(Playlist $playlist) => $playlist->picture = $this->urlGenerator->to($playlist->picture));

        return response()->json($playlists);
    }

    /**
     * @todo use relations
     */
    public function getPlaylistSongs(int $playlistID): JsonResponse
    {
        $songs = PlaylistHasSong::where('playlist_id', $playlistID)->pluck('song_id');

        $result = Song::FetchSongs($songs)
            ->each(fn (Song $song) => $this->imageToURL($song));

        return response()->json($result);
    }

    public function getPlaylistInfo(PlaylistRequest $request): JsonResponse
    {
        $playlist = Playlist::find($request->route()->parameter('playlist_id'));

        $playlist->picture = $this->urlGenerator->to($playlist->picture);

        return response()->json($playlist);
    }

    public function addPlaylist(createPlaylistRequest $request): JsonResponse
    {
        $id = $this->authManager->guard()->id();

        $pictureSaved = $this->saveImage($request->file('picture'), $id);

        if ($pictureSaved === false) {
            return response()->json(['Unable To Save Picture'], 400);
        }

        $playlistName = str_replace('"', '', $request->get('name'));

        if (Playlist::createPlaylist($id, $playlistName, $pictureSaved) === null) {
            return response()->json('unsuccessfully attempt', 400);
        }

        return response()->json('successfully created', 201);
    }

    public function editPlaylist(EditPlaylistRequest $request): JsonResponse
    {
        $id = $this->authManager->guard()->id();

        $playlist = Playlist::find(str_replace('"', '', $request->get('playlist_id')));

        if ($request->file('picture')) {
            $pictureSaved = $this->saveImage($request->file('picture'), $id);

            if ($pictureSaved === false) {
                return response()->json(['Unable To Save Picture'], 400);
            }

            $playlist->picture = $pictureSaved;
        }

        $playlist->name = $request->get('name') ? str_replace('"', '', $request->get('name')) : $playlist->name;

        if (!$playlist->save()) {
            return response()->json('unsuccessfully attempt', 400);
        }

        return response()->json('successfully edited', 201);
    }

    public function deletePlaylist(PlaylistRequest $request): JsonResponse
    {
        $playlist = Playlist::find($request->route()->parameter('playlist_id'));

        if ($playlist->delete() !== true) {
            return response()->json('Unsuccessful delete attempt', 400);
        }

        return response()->json('Successfully deleted', 200);
    }

    public function searchPlaylists(string $input): JsonResponse
    {
        $id = $this->authManager->guard()->id();

        $search_playlist = Playlist::searchPlaylist($id, $input)
            ->each(fn(Playlist $playlist) => $playlist->picture = $this->urlGenerator->to($playlist->picture));

        return response()->json($search_playlist);
    }

    public function addToPlaylist(PlaylistRequest $request): JsonResponse
    {
        $playlistID = $request->route()->parameter('playlist_id');
        $songID = $request->route()->parameter('song_id');

        if (PlaylistHasSong::songInPlaylist($playlistID, $songID)) {
            return response()->json('Song in playlist', 400);
        }

        PlaylistHasSong::addToPlaylist($playlistID, $songID);

        return response()->json('Successfully Added', 201);
    }

    public function removeFromPlaylist(PlaylistRequest $request): JsonResponse
    {
        $playlistID = $request->route()->parameter('playlist_id');
        $songID = $request->route()->parameter('song_id');

        if (!PlaylistHasSong::songInPlaylist($playlistID, $songID)) {
            return response()->json('Song in playlist', 400);
        }

        PlaylistHasSong::removeFromPlaylist($playlistID, $songID);

        return response()->json('Successfully Removed', 201);
    }
}
