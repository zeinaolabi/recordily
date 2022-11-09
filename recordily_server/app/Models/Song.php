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

    public function likes()
    {
        return $this->hasMany(Like::class, 'user_id');
    }

    public function plays()
    {
        return $this->hasMany(Play::class, 'user_id');
    }

    public function user()
    {
        return $this->belongsTo(User::class, 'user_id')->select('name');
    }

    public static function getArtistSongs(int $artist_id, int $limit): Collection
    {
        $is_published = 1;

        return self::where('user_id', $artist_id)
            ->where('is_published', $is_published)
            ->limit($limit)
            ->get();
    }

    public static function getArtistTopSongs(int $limit, int $artist_id): Collection
    {
        return self::select('song_id', DB::raw('count(*) as plays'))
            ->join('plays', 'songs.id', 'song_id')
            ->where('songs.user_id', $artist_id)
            ->groupBy('song_id')
            ->orderBy('plays', 'desc')
            ->limit($limit)
            ->pluck('song_id');
    }

    public static function getArtistUnreleasedSongs(int $id, int $limit): Collection
    {
        $not_published = 0;

        return self::where('is_published', $not_published)
            ->where('user_id', $id)
            ->where('album_id', null)
            ->limit($limit)
            ->get();
    }

    public static function searchForSong(string $input)
    {
        $is_published = 1;

        return self::where('is_published', $is_published)->
        where('name', 'like', '%' . $input . '%')
            ->get();
    }

    public static function fetchSongs(Collection $song_ids)
    {
        return self::whereIn('id', $song_ids)
            ->get()
            ->each(
                function (self $song) {
                    $song->artist_name = $song->user->name;
                    unset($song->user);
                }
            )->toArray();
    }

    public static function publishSong(int $song_id): JsonResponse
    {
        $isPublished = Song::where('id', $song_id)->update(['is_published' => 1]);

        if ($isPublished === null) {
            return response()->json("Unsuccessful publish attempt");
        }

        return response()->json("Successfully published");
    }

    public static function deleteFromAlbum(int $song_id): JsonResponse
    {
        $isDeleted = Song::find($song_id)->delete();

        if ($isDeleted === null) {
            return response()->json("Unsuccessful delete attempt");
        }

        return response()->json("Successfully deleted");
    }
}
