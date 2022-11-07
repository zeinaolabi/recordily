<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Support\Collection;

class Album extends Model
{
    use HasFactory;

    protected $fillable = [
        'name',
        'picture',
        'user_id'
    ];

}
