<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Support\Collection;

class Playlist extends Model
{
    use HasFactory;

    protected $fillable = [
        'name',
        'picture',
        'user_id'
    ];

    public function song()
    {
        return $this->hasMany(Song::class, 'song_id');
    }

    public static function createPlaylist(int $id, string $name, string $picture): Playlist
    {
        return Playlist::create(
            [
            'user_id' => $id,
            'name' => $name,
            'picture' => $picture
            ]
        );
    }

    public static function searchPlaylist(int $userID, string $input): Collection
    {
        return self::where('user_id', $userID)
            ->where('name', 'like', '%' . $input . '%')->get();
    }

    public static function getPlaylists(int $id): Collection
    {
        return self::where('user_id', $id)
            ->orderBy('created_at', 'DESC')
            ->get();
    }
}
