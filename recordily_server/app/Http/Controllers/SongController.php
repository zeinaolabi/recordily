<?php

namespace App\Http\Controllers;

use App\Models\Like;
use App\Models\Play;
use App\Models\Song;
use App\Models\User;
use Exception;
use Illuminate\Contracts\Auth\Factory;
use Illuminate\Contracts\Routing\UrlGenerator;
use Illuminate\Http\JsonResponse;
use Illuminate\Http\Request;
use Illuminate\Support\Collection;
use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\File;
use Illuminate\Support\Facades\Storage;
use Illuminate\Support\Facades\URL;
use Psy\Util\Json;
use wapmorgan\Mp3Info\Mp3Info;

class SongController extends Controller
{
    public function __construct(
        private readonly Factory $authManager,
        private readonly UrlGenerator $urlGenerator
    ) {
    }

    /**
     * @throws Exception
     */
    public function uploadSong(Request $request): JsonResponse
    {
        $id = $this->authManager->guard()->id();
        $metadata = json_decode($request->get('metadata'), true);

        $path = public_path() . '/uploads/' . $id . '/';
        $songPath = $path . $metadata['song_id'] . '/';

        if (!File::exists($path)) {
            File::makeDirectory($path);
        }

        if (!File::exists($songPath)) {
            File::makeDirectory($songPath);
        }

        try {
            $song = $request->file('file')->getContent();
            file_put_contents($songPath . $metadata['chunk_num'], $song);
        } catch (Exception $e) {
            return response()->json($e, 400);
        }

        $chunks = Storage::disk('uploads')->files($id . '/' . $metadata['song_id'] . '/');

        if (count($chunks) == $metadata['chunks_size']) {
            for ($i = 0; $i < count($chunks); $i++) {
                $contents = file_get_contents($songPath . $i);
                file_put_contents($songPath . $metadata['song_id'] . '.mp3', $contents, FILE_APPEND);
                File::delete($songPath . $i);
            }

            try {
                $picture = $request->file('picture');

                $picturePath = '/images/' . $id . '/' . uniqid() . '.' . $picture->extension();
                file_put_contents(public_path() . $picturePath, $picture->getContent());
            } catch (Exception $e) {
                return response()->json(['error' => $e], 400);
            }

            $size = File::size($songPath . $metadata['song_id'] . '.mp3');

            if (!array_key_exists("album_id", $metadata)) {
                $albumID = null;
            } else {
                $albumID = $metadata['album_id'];
            }

            $songSavedPath = '/uploads/' . $id . '/' . $metadata['song_id'] . '/' . $metadata['song_id'] . '.mp3';

            Song::createSong($metadata['name'], $picturePath, $songSavedPath, $size, $id, $albumID);

            return response()->json("Successfully Uploaded", 201);
        }

        return response()->json("Chunk Uploaded Successfully", 201);
    }

    public function getTopPlayedSongs(int $limit): JsonResponse
    {
        $topSongs = Play::getTopSongs("plays", $limit, "plays");

        $result = Song::fetchSongs($topSongs)
            ->each(
                function (Song $song) {
                    $song->artist_name = $song->user->name;
                    $song->picture = $this->urlGenerator->to($song->picture);
                    unset($song->user);
                }
            )->toArray();

        return response()->json($result);
    }

    public function getTopLikedSongs(int $limit): JsonResponse
    {
        $topSongs = Like::getTopSongs("likes", $limit, "likes");

        $result = Song::fetchSongs($topSongs)
            ->each(
                function (Song $song) {
                    $song->artist_name = $song->user->name;
                    $song->picture = $this->urlGenerator->to($song->picture);
                    unset($song->user);
                }
            )->toArray();

        return response()->json($result);
    }

    public function getSuggestedSongs(int $limit): JsonResponse
    {
        $published = 1;

        $songsCount = Song::where('is_published', $published)->count();

        if ($songsCount === 0) {
            return response()->json([]);
        }

        if ($songsCount < $limit) {
            $limit = $songsCount;
        }

        $suggestedSongs = Song::where('is_published', $published)
            ->inRandomOrder()
            ->limit($limit)
            ->get()
            ->each(
                function (Song $song) {
                    $song->artist_name = $song->user->name;
                    unset($song->user);
                }
            );

        return response()->json($suggestedSongs);
    }

    public function searchForSong(string $input): JsonResponse
    {
        $result = (object) [
            'artists' => User::where('name', 'like', '%' . $input . '%')->get(),
            'songs' => Song::searchForSong($input)
        ];

        foreach ($result->songs as $song) {
            $song->artist_name = $song->user->name;
            $song->picture = URL::to($song->picture);
            unset($song->user);
        }

        return response()->json($result);
    }

    public function getLikedSongs(): JsonResponse
    {
        $id = $this->authManager->guard()->id();
        $likedSongs = Like::where('user_id', $id)->pluck('song_id');

        $result = Song::fetchSongs($likedSongs)
            ->each(
                function (Song $song) {
                    $song->artist_name = $song->user->name;
                    $song->picture = $this->urlGenerator->to($song->picture);
                    unset($song->user);
                }
            )->toArray();

        return response()->json($result);
    }

    public function getUnreleasedSongs(int $limit): JsonResponse
    {
        $id = $this->authManager->guard()->id();
        $songs = Song::getArtistUnreleasedSongs($id, $limit)
            ->each(
                function (Song $song) {
                    $song->artist_name = $song->user->name;
                    $song->picture = $this->urlGenerator->to($song->picture);
                    unset($song->user);
                }
            );

        return response()->json($songs);
    }

    public function searchLikedSongs(string $input): JsonResponse
    {
        $id = $this->authManager->guard()->id();

        $searchLiked = Like::searchLikedSongs($id, $input)
            ->each(
                function (Song $song) {
                    $song->artist_name = $song->user->name;
                    unset($song->user);
                }
            );

        return response()->json($searchLiked);
    }

    public function deleteSongFromAlbum(int $song_id): JsonResponse
    {
        return Song::deleteFromAlbum($song_id);
    }

    public function publishSong(int $song_id): JsonResponse
    {
        return Song::publishSong($song_id);
    }

    public function isLiked(int $song_id): JsonResponse
    {
        $id = $this->authManager->guard()->id();

        if (!Song::exists($song_id)) {
            return response()->json("Song Not Found", 400);
        }

        return response()->json(Like::checkIfLiked($id, $song_id));
    }

    public function likeSong(int $song_id): JsonResponse
    {
        $id = $this->authManager->guard()->id();

        if (!Song::exists($song_id)) {
            return response()->json("Song Not Found", 400);
        }

        if (Like::checkIfLiked($id, $song_id)) {
            return response()->json('Already Liked', 400);
        }

        Like::likeSong($id, $song_id);

        return response()->json('Song Liked', 201);
    }

    public function unlikeSong(int $song_id): JsonResponse
    {
        $id = $this->authManager->guard()->id();

        if (!Song::exists($song_id)) {
            return response()->json("Song Not Found", 400);
        }

        if (!Like::checkIfLiked($id, $song_id)) {
            return response()->json('Not Liked', 400);
        }

        Like::unlikeSong($id, $song_id);

        return response()->json('Song Unliked', 201);
    }

    public function getSong(int $song_id): JsonResponse
    {
        $song = Song::find($song_id);

        if ($song === null) {
            return response()->json("Song not found", 400);
        }

        $song->artist_name = $song->user->name;
        $song->picture = $this->urlGenerator->to($song->picture);
        $song->path = $this->urlGenerator->to($song->path);
        unset($song->user);

        return response()->json($song);
    }

    public function playSong(int $song_id): JsonResponse
    {
        $id = $this->authManager->guard()->id();

        $isCreated = Play::create(
            [
            'user_id' => $id,
            'song_id' => $song_id
            ]
        );

        if (!$isCreated) {
            return response()->json("Failed to increment plays", 400);
        }

        return response()->json("Play incremented successfully");
    }
}
