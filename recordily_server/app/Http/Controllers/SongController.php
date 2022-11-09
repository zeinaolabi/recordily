<?php

namespace App\Http\Controllers;

use App\Models\Like;
use App\Models\Play;
use App\Models\Song;
use App\Models\User;
use Exception;
use Illuminate\Http\JsonResponse;
use Illuminate\Http\Request;
use Illuminate\Support\Collection;
use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\File;
use Illuminate\Support\Facades\Storage;
use Illuminate\Support\Facades\URL;

class SongController extends Controller
{
    public function uploadSong(Request $request): bool
    {
        $metadata = json_decode($request->get('metadata'), true);

        $path = public_path() . '/uploads/' . $metadata['user_id'] . '/';
        $song_path = $path . $metadata['song_id'] . '/';

        if (!File::exists($path)) {
            File::makeDirectory($path);
        }

        if (!File::exists($song_path)) {
            File::makeDirectory($song_path);
        }

        try {
            $song = $request->file('file')->getContent();
            file_put_contents($song_path . $metadata['chunk_num'], $song);
            //            $audio = new Mp3Info($song, true);
        } catch (Exception $e) {
            return false;
        }

        $chunks = Storage::disk('uploads')->files($metadata['user_id'] . '/' . $metadata['song_id']);

        if (count($chunks) == $metadata['chunks_size']) {
            for ($i = 0; $i < count($chunks); $i++) {
                $contents = file_get_contents($song_path . $i);
                file_put_contents($song_path . $metadata['song_id'], $contents, FILE_APPEND);
                File::delete($song_path . $i);
            }
        }

        return true;
        //
        //        if(count($chunks) == $request->chunk_size){
        //            $audioFile = "";
        //            foreach ($chunks as $chunk){
        //                var_dump($chunks);
        //            }
        //            var_dump($chunk);
        //            file_put_contents($path . '/'. uniqid(), $audioFile);
        //        }


        //        Song::create([
        //            'name' => $request->name,
        //            'picture' => $request->picture,
        //            'path' => $song_path,
        //            'size' => $song->getSize(),
        //            'type' => $song->extension(),
        //            'time_length' => $audio->duration,
        //            'user_id' => Auth::id(),
        //            'album_id' => $request->album_id
        //        ]);

        //        return response()->json([
        //            "status" => "Successfully saved"
        //        ], 201);
    }

    public function getTopPlayedSongs(int $limit): JsonResponse
    {
        $topSongs = Play::getTopSongs("plays", $limit, "plays");

        $result = Song::fetchSongs($topSongs);

        return response()->json($result);
    }

    public function getTopLikedSongs(int $limit): JsonResponse
    {
        $topSongs = Like::getTopSongs("likes", $limit, "likes");

        $result = Song::fetchSongs($topSongs);

        return response()->json($result);
    }

    public function getSuggestedSongs(int $limit): JsonResponse
    {
        $suggestedSongs = Song::all();

        if ($suggestedSongs->isEmpty()) {
            return response()->json([]);
        }

        $suggestedSongs->random($limit);
        $this->getArtistName($suggestedSongs);

        return response()->json($suggestedSongs);
    }

    public function searchForSong(string $input): JsonResponse
    {
        $is_published = 1;

        $result = (object) [
            'artists' => User::where('name', 'like', '%' . $input . '%')->get(),
            'songs' => Song::searchForSong($input)
        ];

        $this->getArtistName($result->songs);

        return response()->json($result);
    }

    public function getLikedSongs(): JsonResponse
    {
        $id = Auth::id();
        $liked_songs = Like::where('user_id', $id)->pluck('song_id');

        $result = Song::fetchSongs($liked_songs);

        return response()->json($result);
    }

    public function getUnreleasedSongs(int $limit): JsonResponse
    {
        $id = Auth::id();
        $songs = Song::getArtistUnreleasedSongs($id, $limit);

        $this->getPicture($songs);
        $this->getArtistName($songs);

        return response()->json($songs);
    }

    public function searchLikedSongs(string $input): JsonResponse
    {
        $id = Auth::id();

        $search_liked = Like::searchLikedSongs($id, $input);
        $this->getArtistName($search_liked);

        return response()->json($search_liked);
    }

    private function getPicture(Collection $array)
    {
        foreach ($array as $data) {
            $data->picture = URL::to($data->picture);
        }
    }
    private function getArtistName($songs)
    {
        foreach ($songs as $song) {
            $song->artist_name = $song->user->name;
            unset($song->user);
        }
    }
}
