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

    public static function getAlbums(int $artist_id, int $limit): Collection
    {
        return self::where("user_id", $artist_id)
            ->limit($limit)
            ->get();
    }
}
