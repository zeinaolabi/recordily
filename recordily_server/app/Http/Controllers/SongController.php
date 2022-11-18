<?php

namespace App\Http\Controllers;

use App\Http\Requests\SongRequest;
use App\Models\Like;
use App\Models\Play;
use App\Models\Song;
use App\Models\User;
use Exception;
use Illuminate\Contracts\Auth\Factory;
use Illuminate\Contracts\Routing\UrlGenerator;
use Illuminate\Filesystem\Filesystem;
use Illuminate\Filesystem\FilesystemManager;
use Illuminate\Http\JsonResponse;
use Illuminate\Http\Request;
use Illuminate\Support\Collection;
use Illuminate\Support\Facades\File;
use Illuminate\Support\Facades\Storage;

class SongController extends Controller
{
    use InfoTrait;

    public function __construct(
        private readonly Factory $authManager,
        private readonly UrlGenerator $urlGenerator,
        private readonly Filesystem $filesystem,
        private readonly FilesystemManager $filesystemManager
    ) {
    }

    /*
     * @todo use relations
     */
    public function getTopPlayedSongs(int $limit): JsonResponse
    {
        $topSongs = Play::getTopSongs("plays", $limit, "plays");

        $result = Song::fetchSongs($topSongs)
            ->each(fn (Song $song) => $this->imageToURL($song))->toArray();

        return response()->json($result);
    }

    /*
     * @todo use relations
     */
    public function getTopLikedSongs(int $limit): JsonResponse
    {
        $topSongs = Like::getTopSongs("likes", $limit, "likes");

        $result = Song::fetchSongs($topSongs)
            ->each(fn (Song $song) => $this->imageToURL($song))->toArray();

        return response()->json($result);
    }

    public function getSuggestedSongs(int $limit): JsonResponse
    {
        $songsCount = Song::where('is_published', 1)->count();

        if ($songsCount === 0) {
            return response()->json([]);
        }

        $limit = min($songsCount, $limit);

        $suggestedSongs = Song::getSuggestedSongs($limit, 1)
            ->each(fn (Song $song) => $this->imageToURL($song));

        return response()->json($suggestedSongs);
    }

    public function search(string $input): JsonResponse
    {
        $result = (object) [
            'artists' => User::searchForArtist($input),
            'songs' => Song::searchForSong($input)
        ];

        foreach ($result->songs as $song) {
            $this->imageToURL($song);
        }

        foreach ($result->artists as $artist) {
            if ($artist->profile_picture !== null) {
                $artist->profile_picture = $this->urlGenerator->to($artist->profile_picture);
            }
        }

        return response()->json($result);
    }

    public function searchForSong(string $input): JsonResponse
    {
        $songs = Song::searchForSong($input);

        foreach ($songs as $song) {
            $this->imageToURL($song);
        }

        return response()->json($songs);
    }

    /*
     * @todo use relations
     */
    public function getLikedSongs(): JsonResponse
    {
        $id = $this->authManager->guard()->id();
        $likedSongs = Like::where('user_id', $id)->pluck('song_id');

        $result = Song::fetchSongs($likedSongs)
            ->each(fn (Song $song) => $this->imageToURL($song));

        return response()->json($result);
    }

    public function searchLikedSongs(string $input): JsonResponse
    {
        $id = $this->authManager->guard()->id();

        $searchLiked = Like::searchLikedSongs($id, $input)
            ->each(fn (Song $song) => $this->imageToURL($song));

        return response()->json($searchLiked);
    }

    public function deleteSongFromAlbum(SongRequest $request): JsonResponse
    {
        return Song::deleteFromAlbum($request->route()->parameter('song_id'));
    }

    public function publishSong(SongRequest $request): JsonResponse
    {
        return Song::publishSong($request->route()->parameter('song_id'));
    }

    public function isLiked(SongRequest $request): JsonResponse
    {
        $id = $this->authManager->guard()->id();

        return response()->json(Song::songIsLiked($id, $request->route()->parameter('song_id')));
    }

    public function likeSong(SongRequest $request): JsonResponse
    {
        $id = $this->authManager->guard()->id();
        $songID = $request->route()->parameter('song_id');

        if (Song::songIsLiked($id, $songID)) {
            return response()->json('Already Liked', 400);
        }

        Song::likeSong($id, $songID);

        return response()->json('Song Liked', 201);
    }

    public function unlikeSong(SongRequest $request): JsonResponse
    {
        $id = $this->authManager->guard()->id();
        $songID = $request->route()->parameter('song_id');

        if (!Song::songIsLiked($id, $songID)) {
            return response()->json('Not Liked', 400);
        }

        Song::unlikeSong($id, $songID);

        return response()->json('Song Unliked', 201);
    }

    public function getSong(SongRequest $request): JsonResponse
    {
        $song = Song::find($request->route()->parameter('song_id'));

        $this->imageToURL($song);
        $song->path = $this->urlGenerator->to($song->path);

        return response()->json($song);
    }

    public function incrementSongPlays(SongRequest $request): JsonResponse
    {
        $id = $this->authManager->guard()->id();

        Song::incrementSongPlays($id, $request->route()->parameter('song_id'));

        return response()->json("Plays incremented successfully");
    }

    public function getSongLikes(int $song_id): JsonResponse
    {
        return response()->json(Like::where('song_id', $song_id)->count());
    }

    public function getSongViews(int $song_id): JsonResponse
    {
        return response()->json(Play::where('song_id', $song_id)->count());
    }

    public function getSongViewsPerMonth(int $song_id): JsonResponse
    {
        $plays = Play::getSongViewsPerMonth($song_id);

        return response()->json($this->getViews($plays));
    }

    public function getViewsPerMonth(): JsonResponse
    {
        $id = $this->authManager->guard()->id();

        $plays = Play::getViewsPerMonth($id);

        return response()->json($this->getViews($plays));
    }

    public function getUnreleasedSongs(int $limit): JsonResponse
    {
        $id = $this->authManager->guard()->id();
        $songs = Song::getArtistUnreleasedSongs($id, $limit)
            ->each(fn(Song $song) => $this->imageToURL($song));

        return response()->json($songs);
    }

    /**
     * @throws Exception
     * @todo   dependency injection
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

    private function getViews(Collection $plays): array
    {
        $playsCount = [];
        $playsArray = [];

        foreach ($plays as $key => $value) {
            $playsCount[(int)$key] = count($value);
        }

        for ($i = 1; $i <= 12; $i++) {
            if (! isset($playsCount[$i])) {
                $playsArray[] = 0;
            } else {
                $playsArray[] = $playsCount[$i];
            }
        }

        return $playsArray;
    }
}
