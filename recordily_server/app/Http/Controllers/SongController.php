<?php

namespace App\Http\Controllers;

use App\Http\Requests\UploadSongRequest;
use App\Models\Like;
use App\Models\Play;
use App\Models\Song;
use Exception;
use Illuminate\Http\JsonResponse;
use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\DB;
use wapmorgan\Mp3Info\Mp3Info;

class SongController extends Controller
{
    function uploadSong(UploadSongRequest $request): bool{
        $path = public_path() . '/chunks/';

//        if (! $request->hasFile('audio')) {
//            return response()->json([
//                "status" => "File Not Found"
//            ], 400);
//        }

        try {
            $song = $request->file;
            $bString = hex2bin($song);
            $song_path = $path . uniqid();
            file_put_contents($song_path, $bString);
//            $audio = new Mp3Info($song, true);
        }catch (Exception $e) {
            return false;
        }

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

    function getSongs(): JsonResponse
    {
        $allSongs = Song::all();

        return response()->json($allSongs);
    }

    function getTopPlayedSongs(): JsonResponse{

        $topSongs = Play::select('song_id', DB::raw('count(*) as plays'))
            ->groupBy('song_id')
            ->orderBy('plays', 'desc')
            ->limit(5)
            ->pluck('song_id');

        $result = [];
        foreach ($topSongs as $topSong){
            $result[] = Song::find($topSong);
        }

//        $topSongs = Play::select('song_id', DB::raw('count(*) as plays'))
//            ->groupBy('song_id')
//            ->orderBy('plays', 'desc')
//            ->limit(5)
//            ->pluck('song_id');
//
//        $result[] = Song::query()->whereIn($topSongs);
//
//        return response()->json($result);

        return response()->json($result);
    }

    function getTopLikedSongs(): JsonResponse{

        $topSongs = Like::select('song_id', DB::raw('count(*) as likes'))
            ->groupBy('song_id')
            ->orderBy('likes', 'desc')
            ->limit(5)
            ->pluck('song_id');

        $result = [];
        foreach ($topSongs as $topSong){
            $result[] = Song::find($topSong);
        }

        return response()->json($result);
    }

    function getFiveRecentlyPlayed(): JsonResponse{

        $topSongs = Play::select('song_id', DB::raw('count(*) as likes'))
            ->groupBy('song_id')
            ->orderBy('created_at', 'desc')
            ->limit(5)
            ->pluck('song_id');

        $result = [];
        foreach ($topSongs as $topSong){
            $result[] = Song::find($topSong);
        }

        return response()->json($result);
    }


}
