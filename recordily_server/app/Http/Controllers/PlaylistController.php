<?php

namespace App\Http\Controllers;

use App\Http\Requests\PlaylistRequest;
use App\Models\Playlist;
use App\Models\PlaylistHasSong;
use App\Models\Song;
use Exception;
use Illuminate\Http\JsonResponse;
use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\File;
use Illuminate\Support\Facades\URL;
use Psy\Util\Json;

class PlaylistController extends Controller
{
    public function getPlaylists(): JsonResponse
    {
        $id = Auth::id();
        $playlists = Playlist::where('user_id', $id)->get();

        foreach ($playlists as $playlist) {
            $playlist->picture = URL::to($playlist->picture);
        }

        return response()->json($playlists);
    }

    public function getPlaylistSongs(int $playlist_id): JsonResponse
    {
        $songs = PlaylistHasSong::where('playlist_id', $playlist_id)->pluck('song_id');

        $result = $this->saveSongs($songs);

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

        $path = public_path();

        if (!File::exists($path)) {
            File::makeDirectory($path);
        }

        try {
            $picture = $request->file('picture');

            $picture_path = '/images/' . $id . '/' . uniqid() . '.' . $picture->extension();
            file_put_contents($path . $picture_path, $picture->getContent());
        } catch (Exception $e) {
            return response()->json(['error' => $e], 400);
        }

        $playlist_name = str_replace('"', '', $request->get('name'));

        if (!Playlist::createPlaylist($id, $playlist_name, $picture_path)) {
            return response()->json('unsuccessfully attempt', 400);
        }

        return response()->json($picture->extension(), 201);
    }

    private function saveSongs($song_ids): array
    {
        $result = [];
        foreach ($song_ids as $song_id) {
            $song = Song::find($song_id);
            $song->artist_name = $song->user->name;
            unset($song->user);
            $result[] = $song;
        }

        return $result;
    }
}
