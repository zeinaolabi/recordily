<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;
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
        return self::where("user_id", $artist_id)
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
}
