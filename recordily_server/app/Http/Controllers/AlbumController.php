<?php

namespace App\Http\Controllers;

use App\Http\Requests\AlbumRequest;
use App\Http\Requests\createAlbumRequest;
use App\Models\Album;
use App\Models\Song;
use Exception;
use File;
use Illuminate\Contracts\Auth\Factory;
use Illuminate\Contracts\Routing\UrlGenerator;
use Illuminate\Filesystem\Filesystem;
use Illuminate\Filesystem\FilesystemManager;
use Illuminate\Http\JsonResponse;
use Illuminate\Support\Facades\Storage;

class AlbumController extends Controller
{
    use InfoTrait;

    public function __construct(
        private readonly Factory $authManager,
        private readonly UrlGenerator $urlGenerator,
        private readonly Filesystem $filesystem,
        private readonly FilesystemManager $filesystemManager
    ) {
    }

    public function getAlbum(AlbumRequest $request): JsonResponse
    {
        $album = Album::find($request->route()->parameter('album_id'));

        $this->imageToURL($album);

        return response()->json($album);
    }

    public function getAlbums(): JsonResponse
    {
        $id = $this->authManager->guard()->id();

        $albums = Album::where('user_id', $id)->get()
            ->each(fn (Album $album) => $this->imageToURL($album));

        return response()->json($albums);
    }

    public function getAlbumSongs(AlbumRequest $request): JsonResponse
    {
        $songs = Album::find($request->route()->parameter('album_id'))->songs->where('is_published', 1)
            ->each(fn (Song $song) => $this->imageToURL($song));

        return response()->json($songs);
    }

    public function addAlbum(createAlbumRequest $request): JsonResponse
    {
        $id = $this->authManager->guard()->id();

        $pictureSaved = $this->saveImage($request->file('picture'), $id);

        if ($pictureSaved === false) {
            return response()->json(['Unable To Save Picture'], 400);
        }

        $albumName = str_replace('"', '', $request->get('name'));

        if (Album::createAlbum($id, $albumName, $pictureSaved) === null) {
            return response()->json('Unsuccessful Attempt to Create Album', 400);
        }

        return response()->json("Successfully Created", 201);
    }

    public function publishAlbum(AlbumRequest $request): JsonResponse
    {
        return Album::publishAlbum($request->route()->parameter('album_id'));
    }

    public function getUnreleasedAlbums(int $limit): JsonResponse
    {
        $id = $this->authManager->guard()->id();
        $albums = Album::getArtistUnreleasedAlbums($id, $limit)
            ->each(fn (Album $album) => $this->imageToURL($album));

        return response()->json($albums);
    }
}
