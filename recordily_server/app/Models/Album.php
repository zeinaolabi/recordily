<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Support\Collection;

/**
 * App\Models\Album
 *
 * @property int $id
 * @property string $name
 * @property string $picture
 * @property int $user_id
 * @property int $is_published
 * @property \Illuminate\Support\Carbon|null $created_at
 * @property \Illuminate\Support\Carbon|null $updated_at
 * @property-read \App\Models\User|null $user
 * @method static \Illuminate\Database\Eloquent\Builder|Album newModelQuery()
 * @method static \Illuminate\Database\Eloquent\Builder|Album newQuery()
 * @method static \Illuminate\Database\Eloquent\Builder|Album query()
 * @method static \Illuminate\Database\Eloquent\Builder|Album whereCreatedAt($value)
 * @method static \Illuminate\Database\Eloquent\Builder|Album whereId($value)
 * @method static \Illuminate\Database\Eloquent\Builder|Album whereIsPublished($value)
 * @method static \Illuminate\Database\Eloquent\Builder|Album whereName($value)
 * @method static \Illuminate\Database\Eloquent\Builder|Album wherePicture($value)
 * @method static \Illuminate\Database\Eloquent\Builder|Album whereUpdatedAt($value)
 * @method static \Illuminate\Database\Eloquent\Builder|Album whereUserId($value)
 * @mixin \Eloquent
 */
class Album extends Model
{
    use HasFactory;

    protected $fillable = [
        'name',
        'picture',
        'user_id'
    ];

    public function user()
    {
        return $this->belongsTo(User::class, 'user_id')->select('name');
    }

    public static function getAlbums(int $artist_id, int $limit): Collection
    {
        $is_published = 1;

        return self::where("user_id", $artist_id)
            ->where('is_published', $is_published)
            ->limit($limit)
            ->get();
    }

    public static function findPublished(int $id): Collection
    {
        $is_published = 1;

        return self::where('id', $id)
            ->where('is_published', $is_published)
            ->first();
    }
}
