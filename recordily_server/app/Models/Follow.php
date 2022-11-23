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

    public static function artistIsFollowed(int $id, int $artistID): bool
    {
        $isFollowed = self::where('follower_id', $id)
            ->where('followed_id', $artistID)->count();

        if ($isFollowed === 0) {
            return false;
        }

        return true;
    }

    public static function followArtist(int $id, int $artistID): self
    {
        return self::create(
            [
            'follower_id' => $id,
            'followed_id' => $artistID
            ]
        );
    }

    public static function unfollowArtist(int $id, int $artistID): int
    {
        return self::where('follower_id', $id)
            ->where('followed_id', $artistID)
            ->delete();
    }

    public static function searchFollowedArtists(int $id, string $input): Collection
    {
        return User::whereIn(
            'id',
            self::where('follower_id', $id)->pluck('followed_id')
        )->where('name', 'like', '%' . $input . '%')->get();
    }
}
