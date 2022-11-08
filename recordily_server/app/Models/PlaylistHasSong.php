<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

/**
 * App\Models\PlaylistHasSong
 *
 * @property int $id
 * @property int $playlist_id
 * @property int $song_id
 * @property \Illuminate\Support\Carbon|null $created_at
 * @property \Illuminate\Support\Carbon|null $updated_at
 * @method static \Illuminate\Database\Eloquent\Builder|PlaylistHasSong newModelQuery()
 * @method static \Illuminate\Database\Eloquent\Builder|PlaylistHasSong newQuery()
 * @method static \Illuminate\Database\Eloquent\Builder|PlaylistHasSong query()
 * @method static \Illuminate\Database\Eloquent\Builder|PlaylistHasSong whereCreatedAt($value)
 * @method static \Illuminate\Database\Eloquent\Builder|PlaylistHasSong whereId($value)
 * @method static \Illuminate\Database\Eloquent\Builder|PlaylistHasSong wherePlaylistId($value)
 * @method static \Illuminate\Database\Eloquent\Builder|PlaylistHasSong whereSongId($value)
 * @method static \Illuminate\Database\Eloquent\Builder|PlaylistHasSong whereUpdatedAt($value)
 * @mixin \Eloquent
 */
class PlaylistHasSong extends Model
{
    use HasFactory;

    protected $collection = "playlist_has_songs";

    protected $fillable = [
        'playlist_id',
        'song_id'
    ];
}
