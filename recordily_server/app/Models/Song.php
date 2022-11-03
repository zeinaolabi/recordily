<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Song extends Model
{
    use HasFactory;

    protected $fillable = [
        'name',
        'picture',
        'path',
        'size',
        'type',
        'time_length',
        'user_id',
        'album_id'
    ];
}
