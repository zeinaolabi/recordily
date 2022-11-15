<?php

namespace App\Http\Controllers;

use App\Models\Play;
use App\Models\Song;
use App\Models\User;
use Exception;
use Illuminate\Contracts\Auth\Factory;
use Illuminate\Contracts\Routing\UrlGenerator;
use Illuminate\Http\JsonResponse;
use Illuminate\Support\Collection;
use Illuminate\Support\Facades\Auth;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\File;
use Illuminate\Support\Facades\URL;

class UserController extends Controller
{
    public function __construct(
        private readonly Factory $authManager,
        private readonly UrlGenerator $urlGenerator
    ) {
    }

    public function getUserInfo(): JsonResponse
    {
        $id = $this->authManager->guard()->id();
        $user = User::find($id);
        $user->profile_picture = $this->urlGenerator->to($user->profile_picture);

        return response()->json($user);
    }

    public function editProfile(Request $request): JsonResponse
    {
        $id = $this->authManager->guard()->id();
        $user = User::find($id);

        $path = public_path() . '/images/' . $id;

        if (!File::exists($path)) {
            File::makeDirectory($path);
        }

        if ($request->file('profile_picture')) {
            try {
                $picture = $request->file('profile_picture');

                $picturePath = '/images/' . $id . '/' . uniqid() . '.' . $picture->extension();
                file_put_contents(public_path() . $picturePath, $picture->getContent());

                $user->profile_picture = $picturePath;
            } catch (Exception $e) {
                return response()->json(['error' => $e], 400);
            }
        }

        $user->name = $request->get('name') ? str_replace('"', '', $request->get('name')) : $user->name;
        $user->biography = $request->get('biography') ?
            str_replace('"', '', $request->get('biography')) : $user->biography;

        if (!$user->save()) {
            return response()->json('unsuccessfully attempt', 400);
        }

        return response()->json('successfully edited', 201);
    }

    public function getUserTopSongs(int $limit)
    {
        $id = $this->authManager->guard()->id();
        $songs = Play::getUserTopSongs($id, $limit);

        $result = Song::fetchSongs($songs)
            ->each(
                function (Song $song) {
                    $song->artist_name = $song->user->name;
                    $song->picture = $this->urlGenerator->to($song->picture);
                    unset($song->user);
                }
            )->toArray();

        return response()->json($result);
    }

    public function getRecentlyPlayed(int $limit): JsonResponse
    {
        $id = $this->authManager->guard()->id();
        $topSongs = Play::getRecentlyPlayed($id, $limit);

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

    public function getUserSongs(): JsonResponse
    {
        $id = $this->authManager->guard()->id();
        $published = 1;

        $songs = Song::where('user_id', $id)->where('is_published', $published)
            ->get()
            ->each(
                function (Song $song) {
                    $song->artist_name = $song->user->name;
                    $song->picture = $this->urlGenerator->to($song->picture);
                    unset($song->user);
                }
            )->toArray();

        return response()->json($songs);
    }

    public function searchReleasedSongs(string $input): JsonResponse
    {
        $id = $this->authManager->guard()->id();
        $songs = Song::searchReleasedSongs($id, $input)
            ->each(
                function (Song $song) {
                    $song->artist_name = $song->user->name;
                    $song->picture = $this->urlGenerator->to($song->picture);
                    unset($song->user);
                }
            );

        return response()->json($songs);
    }
}
