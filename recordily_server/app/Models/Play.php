<?php

namespace App\Models;

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

    public static function getRecentlyPlayed(int $limit): Collection
    {
        return self::select('song_id')
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


    public function song()
    {
        return $this->belongsTo(Song::class, 'song_id');
    }
}
