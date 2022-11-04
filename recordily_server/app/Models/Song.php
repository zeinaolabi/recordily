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

    public function likes(){
        return $this->hasMany(Like::class, 'user_id');
    }

    public function plays(){
        return $this->hasMany(Play::class, 'user_id');
    }

    public function user(){
        return $this->belongsTo(User::class, 'user_id')->select('name');
    }
}
