<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Ramsey\Collection\Collection;

class Play extends BaseModel
{
    use HasFactory;

    protected $fillable = [
        'user_id',
        'song_id'
    ];

    public static function getRecentlyPlayed(int $limit): Collection{
        return self::select('song_id')
            ->orderBy('created_at', 'desc')
            ->limit($limit)
            ->pluck('song_id');
    }
}