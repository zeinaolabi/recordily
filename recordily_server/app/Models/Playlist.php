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

    public static function exists(int $playlist_id): bool
    {
        return (bool)self::find($playlist_id);
    }

    public static function createPlaylist(int $id, string $name, string $picture): bool
    {
        $isCreated = Playlist::create(
            [
            'user_id' => $id,
            'name' => $name,
            'picture' => $picture
            ]
        );

        if (!$isCreated) {
            return false;
        }

        return true;
    }

    public static function searchPlaylist(int $user_id, string $input): Collection
    {
        return self::where('user_id', '=', $user_id)
            ->where('name', 'like', '%' . $input . '%')->get();
    }

    public static function songInPlaylist(int $song_id, int $playlist_id)
    {
        return (bool)self::where('playlist_id', $playlist_id)
            ->where('song_id', $song_id)->first();
    }

    public static function addToPlaylist(int $playlist_id, int $song_id): self
    {
        return self::create([
            'playlist_id' => $playlist_id,
            'song_id' => $song_id
        ]);
    }

    public static function removeFromPlaylist(int $playlist_id, int $song_id): int
    {
        return self::where('playlist_id', $playlist_id)
            ->where('song_id', $song_id)
            ->delete();
    }
}
