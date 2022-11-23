<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Support\Collection;

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

    public static function searchLikedSongs(int $id, string $input): Collection
    {
        return Song::whereIn(
            'id',
            Like::where('user_id', $id)->get()->pluck('song_id')
        )->where('name', 'like', '%' . $input . '%')->get();
    }
}
