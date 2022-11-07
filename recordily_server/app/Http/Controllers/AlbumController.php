<?php

namespace App\Http\Controllers;

use App\Models\Album;
use Illuminate\Http\JsonResponse;

class AlbumController extends Controller
{
    public function getArtistAlbums(int $artist_id, int $limit): JsonResponse
    {
        $albums = Album::getAlbums($artist_id, $limit);

        return response()->json($albums);
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
