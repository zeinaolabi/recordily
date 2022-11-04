<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class PlaylistHasSong extends Model
{
    protected $collection = "playlist_has_songs";

    use HasFactory;

    protected $fillable = [
        'playlist_id',
        'song_id'
    ];
}