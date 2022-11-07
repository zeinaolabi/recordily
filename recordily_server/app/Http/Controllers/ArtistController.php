<?php

namespace App\Http\Controllers;

use App\Models\Follow;
use Illuminate\Http\JsonResponse;
use Illuminate\Support\Facades\Auth;

class ArtistController extends Controller
{
    public function getFollowedArtists(): JsonResponse
    {
        $id = Auth::id();

        $followed_artists = Follow::getFollowedArtists($id);

        if ($followed_artists->isEmpty()) {
            return response()->json('No followed artists', 204);
        }

        return response()->json($followed_artists);
    }
}
