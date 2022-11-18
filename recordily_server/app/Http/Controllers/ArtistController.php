<?php

namespace App\Http\Controllers;

use App\Http\Requests\ArtistRequest;
use App\Models\Album;
use App\Models\Follow;
use App\Models\Song;
use App\Models\User;
use Illuminate\Contracts\Auth\Factory;
use Illuminate\Contracts\Routing\UrlGenerator;
use Illuminate\Http\JsonResponse;
use Illuminate\Support\Collection;

class ArtistController extends Controller
{
    use InfoTrait;

    public function __construct(
        private readonly Factory $authManager,
        private readonly UrlGenerator $urlGenerator
    ) {
    }

    public function getFollowedArtists(): JsonResponse
    {
        $id = $this->authManager->guard()->id();

        $followedArtists = User::getFollowedArtists($id)
            ->each(fn (User $artist) => $artist->profile_picture = $this->urlGenerator->to($artist->profile_picture));

        return response()->json($followedArtists);
    }

    public function isFollowed(ArtistRequest $request): JsonResponse
    {
        $id = $this->authManager->guard()->id();

        return response()->json(Follow::artistIsFollowed($id, $request->route()->parameter('artist_id')));
    }

    public function followArtist(ArtistRequest $request): JsonResponse
    {
        $id = $this->authManager->guard()->id();
        $artistID = $request->route()->parameter('artist_id');

        if (!User::isArtist($artistID)) {
            return response()->json(false, 400);
        }

        if (Follow::artistIsFollowed($id, $artistID)) {
            return response()->json('Already Followed', 400);
        }

        Follow::followArtist($id, $artistID);

        return response()->json('Artist Followed', 201);
    }

    public function unfollowArtist(ArtistRequest $request): JsonResponse
    {
        $id = $this->authManager->guard()->id();
        $artistID = $request->route()->parameter('artist_id');

        if (!User::isArtist($artistID)) {
            return response()->json(User::isArtist($artistID), 400);
        }

        if (!Follow::artistIsFollowed($id, $artistID)) {
            return response()->json('Not Followed', 400);
        }

        Follow::unfollowArtist($id, $artistID);

        return response()->json('Artist Unfollowed', 201);
    }

    public function getArtistInfo(ArtistRequest $request): JsonResponse
    {
        $artist = User::find($request->route()->parameter('artist_id'));

        if ($artist->profile_picture !== null) {
            $artist->profile_picture = $this->urlGenerator->to($artist->profile_picture);
        }

        return response()->json($artist);
    }

    public function getArtistFollowers(ArtistRequest $request): int
    {
        return Follow::where('followed_id', $request->route()->parameter('artist_id'))->count();
    }

    public function getArtistAlbums(ArtistRequest $request): JsonResponse
    {
        $albums = User::getArtistAlbums(
            $request->route()->parameter('artist_id'),
            $request->route()->parameter('limit')
        )
            ->each(fn (Album $album) => $this->imageToURL($album));

        return response()->json($albums);
    }

    public function getArtistSongs(ArtistRequest $request): JsonResponse
    {
        $songs = User::getArtistSongs(
            $request->route()->parameter('artist_id'),
            $request->route()->parameter('limit')
        )
            ->each(fn (Song $song) => $this->imageToURL($song));

        return response()->json($songs);
    }

    public function getArtistTopSongs(ArtistRequest $request): JsonResponse
    {
        $songs = Song::getArtistTopSongs(
            $request->route()->parameter('artist_id'),
            $request->route()->parameter('limit')
        )
            ->each(fn (Song $song) => $this->imageToURL($song));

        return response()->json($songs);
    }

    public function getArtistTopAlbum(ArtistRequest $request): JsonResponse
    {
        $songs = Song::getArtistTopAlbums(
            $request->route()->parameter('artist_id'),
            $request->route()->parameter('limit')
        )
            ->each(fn (Album $album) => $this->imageToURL($album));

        return response()->json($songs);
    }

    public function getTopArtists(int $limit): JsonResponse
    {
        $songs = Song::getArtists($limit)
            ->each(fn (User $artist) => $artist->profile_picture = $this->urlGenerator->to($artist->profile_picture));

        return response()->json($songs);
    }

    public function searchFollowedArtist(string $input): JsonResponse
    {
        $id = $this->authManager->guard()->id();
        $artists = Follow::searchFollowedArtists($id, $input)
            ->each(fn (User $artist) => $artist->profile_picture = $this->urlGenerator->to($artist->profile_picture));

        return response()->json($artists);
    }

    public function getUnreleasedAlbums(int $limit): JsonResponse
    {
        $id = $this->authManager->guard()->id();
        $albums = User::getArtistAlbums($id, $limit)
            ->each(fn (Album $album) => $this->imageToURL($album));

        return response()->json($albums);
    }
}
