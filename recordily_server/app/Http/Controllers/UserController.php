<?php

namespace App\Http\Controllers;

use App\Models\Play;
use App\Models\Song;
use App\Models\User;
use Exception;
use Illuminate\Contracts\Auth\Factory;
use Illuminate\Contracts\Routing\UrlGenerator;
use Illuminate\Filesystem\Filesystem;
use Illuminate\Filesystem\FilesystemManager;
use Illuminate\Http\JsonResponse;
use Illuminate\Support\Collection;
use Illuminate\Support\Facades\Auth;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\File;
use Illuminate\Support\Facades\URL;

class UserController extends Controller
{
    use InfoTrait;

    public function __construct(
        private readonly Factory $authManager,
        private readonly UrlGenerator $urlGenerator,
        private readonly Filesystem $filesystem,
        private readonly FilesystemManager $filesystemManager
    ) {
    }

    public function getUserInfo(): JsonResponse
    {
        $id = $this->authManager->guard()->id();
        $user = User::find($id);

        if ($user->profile_picture !== null) {
            $user->profile_picture = $this->urlGenerator->to($user->profile_picture);
        }

        return response()->json($user);
    }

    public function editProfile(Request $request): JsonResponse
    {
        $id = $this->authManager->guard()->id();
        $user = User::find($id);

        if ($request->file('profile_picture') !== null) {
            $pictureSaved = $this->saveImage($request->file('profile_picture'), $id);

            if ($pictureSaved === false) {
                return response()->json(['Unable To Save Picture'], 400);
            }

            $user->profile_picture = $pictureSaved;
        }

        $user->name = $request->get('name') ? str_replace('"', '', $request->get('name')) : $user->name;
        $user->biography = $request->get('biography') ?
            str_replace('"', '', $request->get('biography')) : $user->biography;

        if (!$user->save()) {
            return response()->json('unsuccessfully attempt', 400);
        }

        return response()->json('successfully edited', 201);
    }

    /*
     @todo use relations
    */
    public function getUserTopSongs(int $limit)
    {
        $id = $this->authManager->guard()->id();
        $songs = Play::getUserTopSongs($id, $limit);

        $result = Song::fetchSongs($songs)
            ->each(fn(Song $song) => $this->imageToURL($song));

        return response()->json($result);
    }

    /*
     @todo use relations
    */
    public function getRecentlyPlayed(int $limit): JsonResponse
    {
        $id = $this->authManager->guard()->id();
        $topSongs = Play::getRecentlyPlayed($id, $limit);

        $result = Song::fetchSongs($topSongs)
            ->each(fn(Song $song) => $this->imageToURL($song));

        return response()->json($result);
    }

    public function getUserSongs(): JsonResponse
    {
        $id = $this->authManager->guard()->id();

        $songs = Song::where('user_id', $id)->where('is_published', 1)->get()
            ->each(fn(Song $song) => $this->imageToURL($song));

        return response()->json($songs);
    }

    public function searchReleasedSongs(string $input): JsonResponse
    {
        $id = $this->authManager->guard()->id();
        $songs = Song::searchReleasedSongs($id, $input)
            ->each(fn(Song $song) => $this->imageToURL($song));

        return response()->json($songs);
    }
}
