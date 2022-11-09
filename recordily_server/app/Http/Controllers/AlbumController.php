<?php

namespace App\Http\Controllers;

use App\Models\Album;
use App\Models\Song;
use Illuminate\Http\JsonResponse;
use Illuminate\Support\Collection;
use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\URL;

class AlbumController extends Controller
{
    public function getAlbum(int $album_id): JsonResponse
    {
        $album = Album::findPublished($album_id);

        $album->picture = URL::to($album->picture);
        $album->artist_name = $album->user->name;
        unset($album->user);

        return response()->json($album);
    }

    public function getAlbumSongs(int $album_id): JsonResponse
    {
        $songs = Song::where('album_id', $album_id)->get();

        $this->getPicture($songs);
        $this->getArtistName($songs);

        return response()->json($songs);
    }

    public function getUnreleasedAlbums(int $limit): JsonResponse
    {
        $id = Auth::id();
        $albums = Album::getArtistUnreleasedAlbums($id, $limit);

        $this->getPicture($albums);
        $this->getArtistName($albums);

        return response()->json($albums);
    }

//        public function createAlbum(): JsonResponse
//        {
//            $isCreated = Album::create([
//                'user_id' => 5,
//                'picture' => 'test',
//                'name' => 'test5'
//            ]);
//
//            if(!$isCreated){
//                return response()->json('not created');
//            }
//
//            return response()->json(' created');
//        }

    private function getPicture(Collection $array)
    {
        foreach ($array as $data) {
            $data->picture = URL::to($data->picture);
        }
    }

    private function getArtistName($array)
    {
        foreach ($array as $data) {
            $data->artist_name = $data->user->name;
            unset($data->user);
        }
    }
}
