<?php

namespace App\Http\Controllers;

use App\Http\Requests\SongRequest;
use App\Jobs\ProcessSong;
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

    public function uploadSong(Request $request): JsonResponse
    {
        $id = $this->authManager->guard()->id();
        $metadata = json_decode($request->get('metadata'), true);
        $song = base64_encode($request->file('file')->getContent());
        $picture = base64_encode($request->file('picture')->getContent());
        $pictureExtension = $request->file('picture')->extension();

        ProcessSong::dispatch($id, $metadata, $song, $picture, $pictureExtension);

        return response()->json("Successfully uploaded", 201);
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
