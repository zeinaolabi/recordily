<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Like extends BaseModel
{
    use HasFactory;

    protected $fillable = [
        'user_id',
        'song_id'
    ];

    public function song(){
        return $this->belongsTo(Song::class, 'song_id');
    }
}
