<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Http\JsonResponse;
use Illuminate\Support\Collection;
use PhpParser\Node\Expr\BinaryOp\BooleanAnd;

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

    public static function checkIfFollowed(int $id, int $artist_id): bool
    {
        $isFollowed = self::where('follower_id', $id)
            ->where('followed_id', $artist_id)->get();

        if ($isFollowed->isEmpty())
        {
            return false;
        }

        return true;
    }

    public static function followArtist(int $id, int $artist_id): self
    {
        return self::create([
            'follower_id' => $id,
            'followed_id' => $artist_id
        ]);

    }

    public static function unfollowArtist(int $id, int $artist_id): int
    {
        return self::where('follower_id', $id)
            ->where('followed_id', $artist_id)
            ->delete();

    }

}
