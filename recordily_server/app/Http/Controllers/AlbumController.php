<?php

namespace App\Http\Controllers;

use App\Http\Requests\AlbumRequest;
use App\Models\Album;
use App\Models\Song;
use Exception;
use File;
use Illuminate\Http\JsonResponse;
use Illuminate\Support\Collection;
use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\URL;

class AlbumController extends Controller
{
    public function getAlbum(int $album_id): JsonResponse
    {
        $album = Album::where('id', $album_id)->first();

        $album->picture = URL::to($album->picture);
        $album->artist_name = $album->user->name;
        unset($album->user);

        return response()->json($album);
    }

    public function getAlbums(): JsonResponse
    {
        $id = Auth::id();

        $albums = Album::where('user_id', $id)->get();
        $this->getArtistName($albums);

        return response()->json($albums);
    }

    public function getAlbumSongs(int $album_id): JsonResponse
    {
        $songs = Song::where('album_id', $album_id)->where('is_published',0)->get();

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

    public function addAlbum(AlbumRequest $request): JsonResponse
    {
        $id = Auth::id();

        $path = public_path() . '/images/' . $id;

        if (!File::exists($path)) {
            File::makeDirectory($path);
        }

        try {
            $picture = $request->file('picture');

            $picture_path = '/images/' . $id . '/' . uniqid() . '.' . $picture->extension();
            file_put_contents(public_path() . $picture_path, $picture->getContent());
        } catch (Exception $e) {
            return response()->json(['error' => $e], 400);
        }

        $album_name = str_replace('"', '', $request->get('name'));

        if (!Album::createAlbum($id, $album_name, $picture_path)) {
            return response()->json('unsuccessfully attempt', 400);
        }

        return response()->json('successfully created', 201);
    }

    public function publishAlbum(int $album_id): JsonResponse
    {
        return Album::publishAlbum($album_id);
    }

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
