<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class PlaylistHasSong extends Model
{
    use HasFactory;

    protected $collection = "playlist_has_songs";

    protected $fillable = [
        'playlist_id',
        'song_id'
    ];

    public static function songInPlaylist(int $playlist_id, int $song_id)
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
