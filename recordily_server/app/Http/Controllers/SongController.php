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
use Psy\Util\Json;
use wapmorgan\Mp3Info\Mp3Info;

class SongController extends Controller
{
    /**
     * @throws Exception
     */
    public function uploadSong(Request $request): JsonResponse
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
        } catch (Exception $e) {
            return response()->json($e, 400);
        }

        $chunks = Storage::disk('uploads')->files($metadata['user_id'] . '/' . $metadata['song_id'] . '/');

        if (count($chunks) == $metadata['chunks_size']) {
            for ($i = 0; $i < count($chunks); $i++) {
                $contents = file_get_contents($song_path . $i);
                file_put_contents($song_path . $metadata['song_id'], $contents, FILE_APPEND);
                File::delete($song_path . $i);
            }

            try {
                $picture = $request->file('picture');

                $picture_path = '/images/' . $metadata['user_id'] . '/' . uniqid() . '.' . $picture->extension();
                file_put_contents(public_path() . $picture_path, $picture->getContent());
            } catch (Exception $e) {
                return response()->json(['error' => $e], 400);
            }

            $size = File::size($song_path . $metadata['song_id']);

            if (!array_key_exists("album_id", $metadata)) {
                $album_id = null;
            } else {
                $album_id = $metadata['album_id'];
            }

            Song::create(
                [
                'name' => $metadata['name'],
                'picture' => $picture_path,
                'path' => $song_path,
                'type' => 'test',
                'size' => $size,
                'time_length' => 123,
                'user_id' => Auth::id(),
                'album_id' => $album_id
                ]
            );

            return response()->json("Successfully Uploaded", 201);
        }

        return response()->json("Chunk Uploaded Successfully", 201);
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
        $published = 1;

        $songsCount = Song::where('is_published', $published)->count();

        if ($songsCount === 0) {
            return response()->json("No Songs Found", 400);
        }

        if ($songsCount < $limit) {
            $limit = $songsCount;
        }

        $suggestedSongs = Song::where('is_published', $published)->inRandomOrder()->limit($limit)->get();
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

    public function deleteSongFromAlbum(int $song_id): JsonResponse
    {
        return Song::deleteFromAlbum($song_id);
    }

    public function publishSong(int $song_id): JsonResponse
    {
        return Song::publishSong($song_id);
    }

    public function isLiked(int $song_id): JsonResponse
    {
        $id = Auth::id();

        if (!Song::exists($song_id)) {
            return response()->json("Song Not Found", 400);
        }

        return response()->json(Like::checkIfLiked($id, $song_id));
    }

    public function likeSong(int $song_id): JsonResponse
    {
        $id = Auth::id();

        if (!Song::exists($song_id)) {
            return response()->json("Song Not Found", 400);
        }

        if (Like::checkIfLiked($id, $song_id)) {
            return response()->json('Already Liked', 400);
        }

        Like::likeSong($id, $song_id);

        return response()->json('Song Liked', 201);
    }

    public function unlikeSong(int $song_id): JsonResponse
    {
        $id = Auth::id();

        if (!Song::exists($song_id)) {
            return response()->json("Song Not Found", 400);
        }

        if (!Like::checkIfLiked($id, $song_id)) {
            return response()->json('Not Liked', 400);
        }

        Like::unlikeSong($id, $song_id);

        return response()->json('Song Unliked', 201);
    }

    public function getSong(int $song_id): JsonResponse
    {
        $song = Song::find($song_id);

        if ($song === null) {
            return response()->json("Song not found", 400);
        }

        $song->artist_name = $song->user->name;
        $song->picture = URL::to($song->picture);

        return response()->json($song);
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
            $song->picture = URL::to($song->picture);
            unset($song->user);
        }
    }
}
