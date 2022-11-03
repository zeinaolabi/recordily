<?php

namespace App\Http\Controllers;

use App\Http\Requests\UploadSongRequest;
use App\Models\Like;
use App\Models\Play;
use App\Models\Song;
use DirectoryIterator;
use Exception;
use Illuminate\Http\JsonResponse;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\File;
use Illuminate\Support\Facades\Storage;

class SongController extends Controller
{
    function uploadSong(UploadSongRequest $request): bool{
//        if (! $request->hasFile('audio')) {
//            return response()->json([
//                "status" => "File Not Found"
//            ], 400);
//        }

        $path = public_path() . '/uploads/' . $request->user_id . '/';

        if(!File::exists($path)){
            File::makeDirectory($path,0775, true);
        }

//        try {
//            $song = $request->file;
//            $song_path = $path . '/' . $request->chunk_num;
//            file_put_contents($song_path, $song);
////            $audio = new Mp3Info($song, true);
//        }catch (Exception $e) {
//            return false;
//        }

//        $chunks = Storage::disk('public')->allFiles("uploads/" . $request->user_id . '/');
//        var_dump($chunks);

//        $chunks = new DirectoryIterator($path);
//        var_dump(count($chunks));
//        foreach ($chunks as $chunk) {
//            var_dump($chunk['pathName']);
//        }

        $chunks = Storage::disk('uploads')->files($request->user_id);

        if(count($chunks) == $request->chunk_size){
            $files = [];
            for($i =0; $i < count($chunks); $i++){
                $files[] = file_get_contents($path . $i);
            }
            file_put_contents($path . '/'. uniqid(), $files);
        }



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
