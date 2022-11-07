<?php

namespace App\Http\Controllers;

use App\Models\Album;
use App\Models\Follow;
use App\Models\Song;
use App\Models\User;
use Illuminate\Http\JsonResponse;
use Illuminate\Support\Collection;
use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\URL;

class ArtistController extends Controller
{
    public function getFollowedArtists(): JsonResponse
    {
        $id = Auth::id();

        $followed_artists = Follow::getFollowedArtists($id);

        return response()->json($followed_artists);
    }

    public function isFollowed(int $artist_id): JsonResponse
    {
        $id = Auth::id();

        return response()->json(Follow::checkIfFollowed($id, $artist_id));
    }

    public function followArtist(int $artist_id): JsonResponse
    {
        $id = Auth::id();

        if (!User::isArtist($artist_id)) {
            return response()->json(User::isArtist($artist_id), 400);
        }

        if (Follow::checkIfFollowed($id, $artist_id)) {
            return response()->json('Already Followed', 400);
        }

        Follow::followArtist($id, $artist_id);

        return response()->json('Artist Followed', 201);
    }

    public function unfollowArtist(int $artist_id): JsonResponse
    {
        $id = Auth::id();

        if (!User::isArtist($artist_id)) {
            return response()->json(User::isArtist($artist_id), 400);
        }

        if (!Follow::checkIfFollowed($id, $artist_id)) {
            return response()->json('Not Followed', 400);
        }

        Follow::unfollowArtist($id, $artist_id);

        return response()->json('Artist Unfollowed', 201);
    }

    public function getArtistInfo(int $artist_id): JsonResponse
    {
        $artist = User::find($artist_id);

        if (!$artist) {
            return response()->json('Invalid ID', 400);
        }

        return response()->json($artist);
    }

    public function getArtistFollowers(int $artist_id): int
    {
        return Follow::where('followed_id', $artist_id)->count();
    }

    public function getArtistAlbums(int $artist_id, int $limit): JsonResponse
    {
        $albums = Album::getAlbums($artist_id, $limit);

        $this->getPicture($albums);
        $this->getArtistName($albums);

        return response()->json($albums);
    }

    public function getArtistSongs(int $artist_id, int $limit): JsonResponse
    {
        $songs = Song::getArtistSongs($artist_id, $limit);

        $this->getPicture($songs);
        $this->getArtistName($songs);

        return response()->json($songs);
    }

    public function getArtistTopSongs(int $artist_id, int $limit): JsonResponse
    {
        $songs = Song::getArtistTopSongs($limit, $artist_id);

        $this->getPicture($songs);
        $this->getArtistName($songs);

        return response()->json($songs);
    }

    public function searchFollowedArtist(string $input): JsonResponse
    {
        $id = Auth::id();

        $artists = Follow::searchFollowedArtists($id, $input);

        return response()->json($artists);
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
