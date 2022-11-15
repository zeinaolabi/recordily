<?php

namespace App\Http\Controllers;

use App\Models\Album;
use App\Models\Follow;
use App\Models\Like;
use App\Models\Play;
use App\Models\Song;
use App\Models\User;
use Illuminate\Contracts\Auth\Factory;
use Illuminate\Contracts\Routing\UrlGenerator;
use Illuminate\Http\JsonResponse;
use Illuminate\Support\Collection;
use Illuminate\Support\Facades\URL;

class ArtistController extends Controller
{
    public function __construct(
        private readonly Factory $authManager,
        private readonly UrlGenerator $urlGenerator
    ) {
    }

    public function getFollowedArtists(): JsonResponse
    {
        $id = $this->authManager->guard()->id();
        $followedArtists = Follow::getFollowedArtists($id);

        return response()->json($followedArtists);
    }

    public function isFollowed(int $artistID): JsonResponse
    {
        $id = $this->authManager->guard()->id();

        return response()->json(Follow::checkIfFollowed($id, $artistID));
    }

    public function followArtist(int $artistID): JsonResponse
    {
        $id = $this->authManager->guard()->id();

        if (!User::isArtist($artistID)) {
            return response()->json(User::isArtist($artistID), 400);
        }

        if (Follow::checkIfFollowed($id, $artistID)) {
            return response()->json('Already Followed', 400);
        }

        Follow::followArtist($id, $artistID);

        return response()->json('Artist Followed', 201);
    }

    public function unfollowArtist(int $artistID): JsonResponse
    {
        $id = $this->authManager->guard()->id();

        if (!User::isArtist($artistID)) {
            return response()->json(User::isArtist($artistID), 400);
        }

        if (!Follow::checkIfFollowed($id, $artistID)) {
            return response()->json('Not Followed', 400);
        }

        Follow::unfollowArtist($id, $artistID);

        return response()->json('Artist Unfollowed', 201);
    }

    public function getArtistInfo(int $artistID): JsonResponse
    {
        $artist = User::find($artistID);

        if (!$artist) {
            return response()->json('Invalid ID', 400);
        }
        $artist->profile_picture = $this->urlGenerator->to($artist->profile_picture);

        return response()->json($artist);
    }

    public function getArtistFollowers(int $artistID): int
    {
        return Follow::where('followed_id', $artistID)->count();
    }

    public function getArtistAlbums(int $artistID, int $limit): JsonResponse
    {
        $albums = Album::getAlbums($artistID, $limit)
            ->each(
                function (Album $album) {
                    $album->artist_name = $album->user->name;
                    $album->picture = $this->urlGenerator->to($album->picture);
                    unset($album->user);
                }
            );

        return response()->json($albums);
    }

    public function getArtistSongs(int $artistID, int $limit): JsonResponse
    {
        $songs = Song::getArtistSongs($artistID, $limit)
            ->each(
                function (Song $song) {
                    $song->artist_name = $song->user->name;
                    $song->picture = $this->urlGenerator->to($song->picture);
                    unset($song->user);
                }
            );

        return response()->json($songs);
    }

    public function getArtistTopSongs(int $artistID, int $limit): JsonResponse
    {
        $songs = Song::getArtistTopSongs($limit, $artistID)
            ->each(
                function (Song $song) {
                    $song->artist_name = $song->user->name;
                    $song->picture = $this->urlGenerator->to($song->picture);
                    unset($song->user);
                }
            );

        return response()->json($songs);
    }

    public function searchFollowedArtist(string $input): JsonResponse
    {
        $id = $this->authManager->guard()->id();
        $artists = Follow::searchFollowedArtists($id, $input);

        return response()->json($artists);
    }

    public function getViewsPerMonth(): JsonResponse
    {
        $id = $this->authManager->guard()->id();

        $plays = Play::getViewsPerMonth($id);

        return response()->json($this->getViews($plays));
    }

    public function getSongViewsPerMonth(int $song_id): JsonResponse
    {
        $plays = Play::getSongViewsPerMonth($song_id);

        return response()->json($this->getViews($plays));
    }

    public function getSongLikes(int $song_id): JsonResponse
    {
        $likes = Like::where('song_id', $song_id)->count();

        return response()->json($likes);
    }

    public function getSongViews(int $song_id): JsonResponse
    {
        $views = Play::where('song_id', $song_id)->count();

        return response()->json($views);
    }

    private function getViews(Collection $plays): array
    {
        $playsCount = [];
        $playsArray = [];

        foreach ($plays as $key => $value) {
            $playsCount[(int)$key] = count($value);
        }

        for ($i = 1; $i <= 12; $i++) {
            if (!empty($playsCount[$i])) {
                $playsArray[] = $playsCount[$i];
            } else {
                $playsArray[] = 0;
            }
        }

        return $playsArray;
    }
}
