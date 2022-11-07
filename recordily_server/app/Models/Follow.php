<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Support\Collection;

class Follow extends Model
{
    use HasFactory;

    protected $fillable = [
        'follower_id',
        'followed_id'
    ];

    public static function getFollowedArtists(int $id): Collection
    {
        $artist_type_id = 0;

        return User::whereIn(
            'id',
            function ($query) use ($artist_type_id, $id) {
                $query
                    ->select('followed_id')
                    ->from(with(new self())->getTable())
                    ->where('user_type_id', $artist_type_id)
                    ->where('follower_id', $id);
            }
        )->get();
    }
}
