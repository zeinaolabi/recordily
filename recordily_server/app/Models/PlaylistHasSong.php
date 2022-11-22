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

    public function songs()
    {
        return $this->HasMany(Song::class, 'song_id')->select('song_id');
    }

    public static function songInPlaylist(int $playlistID, int $songID)
    {
        return (bool)self::where('playlist_id', $playlistID)
            ->where('song_id', $songID)->first();
    }

    public static function addToPlaylist(int $playlistID, int $songID): self
    {
        return self::create(
            [
            'playlist_id' => $playlistID,
            'song_id' => $songID
            ]
        );
    }

    public static function removeFromPlaylist(int $playlistID, int $songID): int
    {
        return self::where('playlist_id', $playlistID)
            ->where('song_id', $songID)
            ->delete();
    }
}
