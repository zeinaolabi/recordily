<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Http\JsonResponse;
use Illuminate\Support\Collection;
use Illuminate\Support\Facades\DB;

class Song extends Model
{
    use HasFactory;

    protected $fillable = [
        'name',
        'picture',
        'path',
        'size',
        'type',
        'time_length',
        'user_id',
        'album_id'
    ];

    public function user()
    {
        return $this->belongsTo(User::class, 'user_id')->select('name');
    }

    public static function getArtistTopSongs(int $artistID, int $limit): Collection
    {
        return self::whereIn(
            'id',
            self::select('song_id', DB::raw('count(*) as plays'))
                ->join('plays', 'songs.id', 'song_id')
                ->where('songs.user_id', $artistID)
                ->groupBy('song_id')
                ->orderBy('plays', 'desc')
                ->limit($limit)
                ->pluck('song_id')
        )->get();
    }

    public static function getArtistTopAlbums(int $artistID, int $limit): Collection
    {
        return Album::whereIn(
            'id',
            self::select('album_id', DB::raw('count(*) as plays'))
                ->join('plays', 'songs.id', 'song_id')
                ->where('songs.user_id', $artistID)
                ->groupBy('album_id')
                ->orderBy('plays', 'desc')
                ->limit($limit)
                ->pluck('album_id')
        )->get();
    }

    public static function getArtists(int $limit): Collection
    {
        return User::whereIn(
            'id',
            self::select('songs.user_id', DB::raw('count(*) as plays'))
                ->join('plays', 'songs.id', 'song_id')
                ->groupBy('songs.user_id')
                ->orderBy('plays', 'desc')
                ->limit($limit)
                ->pluck('songs.user_id')
        )->get();
    }

    public static function searchForSong(string $input): Collection
    {
        return self::where('is_published', 1)->
        where('name', 'like', '%' . $input . '%')
            ->get();
    }

    public static function fetchSongs(Collection $songIDs): Collection
    {
        return self::whereIn('id', $songIDs)
            ->get();
    }

    public static function publishSong(int $songID): JsonResponse
    {
        $isPublished = Song::where('id', $songID)->update(['is_published' => 1]);

        if ($isPublished === null) {
            return response()->json("Unsuccessful publish attempt");
        }

        return response()->json("Successfully published");
    }

    public static function deleteFromAlbum(int $songID): JsonResponse
    {
        $isDeleted = Song::find($songID)->delete();

        if (!$isDeleted) {
            return response()->json("Unsuccessful delete attempt");
        }

        return response()->json("Successfully deleted");
    }

    public static function searchReleasedSongs(int $userID, string $input): Collection
    {
        return self::where('user_id', $userID)
            ->where('is_published', 1)
            ->where('name', 'like', '%' . $input . '%')->get();
    }

    public static function getSuggestedSongs(int $limit, int $published): Collection
    {
        return self::where('is_published', $published)
            ->inRandomOrder()
            ->limit($limit)
            ->get();
    }

    public static function songIsLiked(int $id, int $songID): bool
    {
        $isLiked = Like::where('user_id', $id)
            ->where('song_id', $songID)->count();

        return $isLiked !== 0;
    }

    public static function likeSong(int $id, int $songID): Like
    {
        return Like::create(
            [
            'user_id' => $id,
            'song_id' => $songID
            ]
        );
    }

    public static function unlikeSong(int $id, int $songID): int
    {
        return Like::where('user_id', $id)
            ->where('song_id', $songID)
            ->delete();
    }

    public static function incrementSongPlays(int $id, int $songID): Play
    {
        return Play::create(
            [
            'user_id' => $id,
            'song_id' => $songID
            ]
        );
    }

    public static function getArtistUnreleasedSongs(int $id, int $limit): Collection
    {
        return self::where('is_published', 0)
            ->where('user_id', $id)
            ->where('album_id', null)
            ->limit($limit)
            ->get();
    }

    public static function createSong(
        string $name,
        string $picturePath,
        string $songPath,
        int $size,
        int $userID,
        ?int $albumID
    ): self {
        return self::create(
            [
                'name' => $name,
                'picture' => $picturePath,
                'path' => $songPath,
                'size' => $size,
                'user_id' => $userID,
                'album_id' => $albumID
            ]
        );
    }
}
