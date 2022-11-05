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
}
