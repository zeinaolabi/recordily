<?php

namespace App\Http\Controllers;

use App\Http\Requests\AlbumRequest;
use App\Models\Album;
use App\Models\Song;
use Exception;
use File;
use Illuminate\Contracts\Auth\Factory;
use Illuminate\Contracts\Routing\UrlGenerator;
use Illuminate\Http\JsonResponse;
use Illuminate\Support\Collection;
use Illuminate\Support\Facades\URL;

class AlbumController extends Controller
{
    public function __construct(
        private readonly Factory $authManager,
        private readonly UrlGenerator $urlGenerator
    ) {
    }

    public function getAlbum(int $albumID): JsonResponse
    {
        $album = Album::where('id', $albumID)->first();

        $album->picture = $this->urlGenerator->to($album->picture);
        $album->artist_name = $album->user->name;
        unset($album->user);

        return response()->json($album);
    }

    public function getAlbums(): JsonResponse
    {
        $id = $this->authManager->guard()->id();

        $albums = Album::where('user_id', $id)
            ->get()
            ->each(
                function (Album $album) {
                    $album->artist_name = $album->user->name;
                    unset($album->user);
                }
            );

        return response()->json($albums);
    }

    public function getAlbumSongs(int $albumID): JsonResponse
    {
        $songs = Song::where('album_id', $albumID)
            ->where('is_published', 0)
            ->get()
            ->each(
                function (Song $song) {
                    $song->artist_name = $song->user->name;
                    $song->picture = $this->urlGenerator->to($song->picture);
                    unset($song->user);
                }
            );

        return response()->json($songs);
    }

    public function getUnreleasedAlbums(int $limit): JsonResponse
    {
        $id = $this->authManager->guard()->id();
        $albums = Album::getArtistUnreleasedAlbums($id, $limit)
            ->each(
                function (Album $album) {
                    $album->artist_name = $album->user->name;
                    $album->picture = $this->urlGenerator->to($album->picture);
                    unset($album->user);
                }
            );

        return response()->json($albums);
    }

    public function addAlbum(AlbumRequest $request): JsonResponse
    {
        $id = $this->authManager->guard()->id();

        $path = public_path() . '/images/' . $id;

        if (!File::exists($path)) {
            File::makeDirectory($path);
        }

        try {
            $picture = $request->file('picture');

            $picturePath = '/images/' . $id . '/' . uniqid() . '.' . $picture->extension();
            file_put_contents(public_path() . $picturePath, $picture->getContent());
        } catch (Exception $e) {
            return response()->json(['error' => $e], 400);
        }

        $albumName = str_replace('"', '', $request->get('name'));

        if (!Album::createAlbum($id, $albumName, $picturePath)) {
            return response()->json('unsuccessfully attempt', 400);
        }

        return response()->json('successfully created', 201);
    }

    public function publishAlbum(int $album_id): JsonResponse
    {
        return Album::publishAlbum($album_id);
    }
}
