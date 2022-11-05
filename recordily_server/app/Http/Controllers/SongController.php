<?php

namespace App\Http\Controllers;

use App\Http\Requests\UploadSongRequest;
use App\Models\Like;
use App\Models\Play;
use App\Models\Playlist;
use App\Models\PlaylistHasSong;
use App\Models\Song;
use App\Models\User;
use Exception;
use Illuminate\Http\JsonResponse;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\File;
use Illuminate\Support\Facades\Storage;

class SongController extends Controller
{
    function uploadSong(Request $request): bool{
        $metadata = json_decode($request->get('metadata'), true);

        $path = public_path() . '/uploads/' . $metadata['user_id'] . '/';
        $song_path = $path . $metadata['song_id'] . '/';

        if(!File::exists($path)){
            File::makeDirectory($path);
        }

        if(!File::exists($song_path)){
            File::makeDirectory($song_path);
        }

//        $chunks = Storage::disk('public')->allFiles("uploads/" . $request->user_id . '/');
//        var_dump($chunks);
//
//        $chunks = new DirectoryIterator($path);
//        var_dump(count($chunks));
//        foreach ($chunks as $chunk) {
//            var_dump($chunk['pathName']);
//        }

        try {
            $song = $request->file('file')->getContent();
            file_put_contents($song_path . $metadata['chunk_num'], $song);
//            $audio = new Mp3Info($song, true);
        }catch (Exception $e) {
            return false;
        }

        $chunks = Storage::disk('uploads')->files($metadata['user_id'] . '/' . $metadata['song_id']);

        if(count($chunks) == $metadata['chunks_size']){
            for($i =0 ; $i < count($chunks); $i++){
                $contents = file_get_contents($song_path . $i);
                file_put_contents($song_path . $metadata['song_id'], $contents, FILE_APPEND);
                File::delete($song_path . $i);
            }
        }

        return true;
//
//        if(count($chunks) == $request->chunk_size){
//            $audioFile = "";
//            foreach ($chunks as $chunk){
//                var_dump($chunks);
//            }
//            var_dump($chunk);
//            file_put_contents($path . '/'. uniqid(), $audioFile);
//        }


//        Song::create([
//            'name' => $request->name,
//            'picture' => $request->picture,
//            'path' => $song_path,
//            'size' => $song->getSize(),
//            'type' => $song->extension(),
//            'time_length' => $audio->duration,
//            'user_id' => Auth::id(),
//            'album_id' => $request->album_id
//        ]);

        return true;
//        return response()->json([
//            "status" => "Successfully saved"
//        ], 201);
    }

    function getSongs(): JsonResponse{
        $allSongs = User::all();

        return response()->json($allSongs);
    }

    function getTopPlayedSongs(int $limit): JsonResponse{
        $topSongs = Play::getTopSongs("plays", $limit, "plays");

        $result = $this->saveSongs($topSongs);

        return response()->json($result);
    }

    function getTopLikedSongs(int $limit): JsonResponse{
        $topSongs = Like::getTopSongs("likes", $limit, "likes");

        $result = $this->saveSongs($topSongs);

        return response()->json($result);
    }

    function getRecentlyPlayed(int $limit): JsonResponse{
        $topSongs = Play::getRecentlyPlayed($limit);

        $result = $this->saveSongs($topSongs);

        return response()->json($result);
    }

    function getSuggestedSongs(int $limit): JsonResponse{
        $suggestedSongs = Song::all()->random($limit);

        $this->getArtistName($suggestedSongs);

        return response()->json($suggestedSongs);
    }

    function searchForSong(string $input): JsonResponse{
        $result = (object) [
            'artists' => User::where('name', 'like', '%' . $input . '%')->get(),
            'songs' => Song::where('name', 'like', '%' . $input . '%')->get()
        ];

        $this->getArtistName($result->songs);

        return response()->json($result);
    }

    function getLikedSongs(): JsonResponse{
        $id = Auth::id();

        $liked_songs = Like::where('user_id', $id)->pluck('song_id');

        $result = $this->saveSongs($liked_songs);

        return response()->json($result);
    }

    function getPlaylists(): JsonResponse{
        $id = Auth::id();

        $playlists = Playlist::where('user_id', $id)->get();

        return response()->json($playlists);
    }

    function getPlaylistSongs(int $playlist_id): JsonResponse{
        $songs = PlaylistHasSong::where('playlist_id', $playlist_id)->pluck('song_id');

        $result = $this->saveSongs($songs);

        return response()->json($result);
    }

    function saveSongs($song_ids): array{
        $result = [];
        foreach ($song_ids as $song_id){
            $song = Song::find($song_id);
            $song->artist_name = $song->user->name;
            unset($song->user);
            $result[] = $song;
        }

        return $result;
    }

    function getArtistName($songs){
        foreach ($songs as $song) {
            $song->artist_name = $song->user->name;
            unset($song->user);
        }
    }
}
