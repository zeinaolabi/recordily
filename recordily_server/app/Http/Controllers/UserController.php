<?php

namespace App\Http\Controllers;

use App\Models\Play;
use App\Models\Song;
use App\Models\User;
use Exception;
use Illuminate\Http\JsonResponse;
use Illuminate\Support\Collection;
use Illuminate\Support\Facades\Auth;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\URL;

class UserController extends Controller
{
    public function getUserInfo(): JsonResponse
    {
        $id = Auth::id();
        $user = User::find($id);

        return response()->json($user);
    }

    public function editProfile(Request $request): JsonResponse
    {
        $id = Auth::id();
        $user = User::find($id);

        if ($request->file('profile_picture')) {
            try {
                $picture = $request->file('profile_picture');

                $picture_path = '/images/' . $id . '/' . uniqid() . '.' . $picture->extension();
                file_put_contents(public_path() . $picture_path, $picture->getContent());

                $user->picture = $picture_path;
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
        $id = Auth::id();
        $songs = Play::getUserTopSongs($id, $limit);

        $this->saveSongs($songs);

        return response()->json($songs);
    }

    public function getRecentlyPlayed(int $limit): JsonResponse
    {
        $id = Auth::id();
        $topSongs = Play::getRecentlyPlayed($id, $limit);

        if ($topSongs->isEmpty()) {
            return response()->json([]);
        }

        $result = $this->saveSongs($topSongs);

        return response()->json($result);
    }

    private function saveSongs($song_ids): array
    {
        $result = [];
        $songs = Song::whereIn('id', $song_ids)->get();
        foreach ($songs as $song) {
            $song->artist_name = $song->user->name;
            $song->picture = URL::to($song->picture);
            unset($song->user);
            $result[] = $song;
        }

        return $result;
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