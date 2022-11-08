<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Http\JsonResponse;
use Illuminate\Support\Collection;
use PhpParser\Node\Expr\BinaryOp\BooleanAnd;

/**
 * App\Models\Follow
 *
 * @property int $id
 * @property int $follower_id
 * @property int $followed_id
 * @property \Illuminate\Support\Carbon|null $created_at
 * @property \Illuminate\Support\Carbon|null $updated_at
 * @method static \Illuminate\Database\Eloquent\Builder|Follow newModelQuery()
 * @method static \Illuminate\Database\Eloquent\Builder|Follow newQuery()
 * @method static \Illuminate\Database\Eloquent\Builder|Follow query()
 * @method static \Illuminate\Database\Eloquent\Builder|Follow whereCreatedAt($value)
 * @method static \Illuminate\Database\Eloquent\Builder|Follow whereFollowedId($value)
 * @method static \Illuminate\Database\Eloquent\Builder|Follow whereFollowerId($value)
 * @method static \Illuminate\Database\Eloquent\Builder|Follow whereId($value)
 * @method static \Illuminate\Database\Eloquent\Builder|Follow whereUpdatedAt($value)
 * @mixin \Eloquent
 */
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

        if ($isFollowed->isEmpty()) {
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

    public static function searchFollowedArtists(int $id, string $input): Collection
    {
        return User::whereIn(
            'id',
            function ($query) use ($id) {
                $query
                    ->select('followed_id')
                    ->from(with(new Follow())->getTable())
                    ->where('follower_id', $id);
            }
        )->where('name', 'like', '%' . $input . '%')->get();
    }
}
