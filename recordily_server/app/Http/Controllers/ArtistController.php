<?php

namespace App\Http\Controllers;

use App\Models\Follow;
use App\Models\User;
use Illuminate\Http\JsonResponse;
use Illuminate\Support\Facades\Auth;

class ArtistController extends Controller
{
    public function getFollowedArtists(): JsonResponse
    {
        $id = Auth::id();

        $followed_artists = Follow::getFollowedArtists($id);

        if ($followed_artists->isEmpty()) {
            return response()->json('No followed artists', 400);
        }

        return response()->json($followed_artists);
    }

    public function isFollowed(int $artist_id): bool
    {
        $id = Auth::id();

        return Follow::checkIfFollowed($id, $artist_id);
    }

    public function followArtist(int $artist_id): JsonResponse
    {
        $id = Auth::id();

        if(!User::isArtist($artist_id))
        {
            return response()->json(User::isArtist($artist_id), 400);
        }

        if (Follow::checkIfFollowed($id, $artist_id))
        {
            return response()->json('Already Followed', 400);
        }

        Follow::followArtist($id, $artist_id);

        return response()->json('Artist Followed', 201);
    }

    public function unfollowArtist(int $artist_id): JsonResponse
    {
        $id = Auth::id();

        if(!User::isArtist($artist_id))
        {
            return response()->json(User::isArtist($artist_id), 400);
        }

        if (!Follow::checkIfFollowed($id, $artist_id))
        {
            return response()->json('Not Followed', 400);
        }

        Follow::unfollowArtist($id, $artist_id);

        return response()->json('Artist Unfollowed', 201);
    }
}
