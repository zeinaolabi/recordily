<?php

namespace App\Http\Controllers;

use App\Models\Playlist;
use App\Models\PlaylistHasSong;
use App\Models\Song;
use Illuminate\Http\JsonResponse;
use Illuminate\Support\Facades\Auth;

class PlaylistController extends Controller
{
    public function getPlaylists(): JsonResponse
    {
        $id = Auth::id();

        $playlists = Playlist::where('user_id', $id)->get();

        return response()->json($playlists);
    }

    public function getPlaylistSongs(int $playlist_id): JsonResponse
    {
        $songs = PlaylistHasSong::where('playlist_id', $playlist_id)->pluck('song_id');

        $result = $this->saveSongs($songs);

        return response()->json($result);
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
