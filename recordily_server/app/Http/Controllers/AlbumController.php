<?php

namespace App\Http\Controllers;

use App\Models\Album;
use App\Models\Song;
use Illuminate\Http\JsonResponse;
use Illuminate\Support\Facades\URL;

class AlbumController extends Controller
{
    public function getAlbum(int $album_id): JsonResponse
    {
        $album = Album::find($album_id);

        $album->picture = URL::to($album->picture);

        return response()->json($album);
    }

    public function getAlbumSongs(int $album_id): JsonResponse
    {
        $songs = Song::where('album_id', $album_id)->get();

        foreach ($songs as $song){
            $song->picture = URL::to($song->picture);
        }

        return response()->json($songs);
    }
//    public function createAlbum(): JsonResponse
//    {
//        $isCreated = Album::create([
//            'user_id' => 8,
//            'picture' => 'test',
//            'name' => 'test'
//        ]);
//
//        if(!$isCreated){
//            return response()->json('not created');
//        }
//
//        return response()->json(' created');
//    }
}
