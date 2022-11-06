<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Like extends BaseModel
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

    public static function searchLikedSongs(int $id, string $input): array
    {
        return Like::where('likes.user_id', $id)
            ->join("songs", "likes.song_id", "=", "songs.id")
            ->where('name', 'like', '%' . $input . '%')->get();
    }
}
