<?php

namespace App\Models;

use Carbon\Carbon;
use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Support\Collection;
use Illuminate\Support\Facades\DB;

class Play extends BaseModel
{
    use HasFactory;

    protected $fillable = [
        'user_id',
        'song_id'
    ];

    public function song()
    {
        return $this->belongsTo(Song::class, 'song_id');
    }

    public static function getRecentlyPlayed(int $id, int $limit): Collection
    {
        return self::select('song_id')
            ->where('user_id', $id)
            ->orderBy('created_at', 'desc')
            ->limit($limit)
            ->pluck('song_id');
    }

    public static function getUserTopSongs(int $id, int $limit): Collection
    {
        return self::select('song_id', DB::raw('count(*) as plays'))
            ->groupBy('song_id')
            ->where('user_id', $id)
            ->orderBy('plays', 'desc')
            ->limit($limit)
            ->pluck('song_id');
    }

    public static function getViewsPerMonth(int $id): Collection
    {
        return self::select('plays.id', 'plays.created_at')
            ->join('songs', 'songs.id', '=', 'song_id')
            ->whereYear('plays.created_at', '=', date("Y"))
            ->where('songs.user_id', $id)
            ->get()
            ->groupBy(fn ($date) => Carbon::parse($date->created_at)->format('m'));
    }

    public static function getSongViewsPerMonth(int $songID): Collection
    {
        return self::select('plays.id', 'plays.created_at')
            ->whereYear('plays.created_at', '=', date("Y"))
            ->where('song_id', $songID)
            ->get()
            ->groupBy(fn ($date) => Carbon::parse($date->created_at)->format('m'));
    }
}
