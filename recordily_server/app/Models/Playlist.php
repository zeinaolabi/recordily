<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Support\Collection;

/**
 * App\Models\Playlist
 *
 * @property int $id
 * @property string $name
 * @property string $picture
 * @property int $user_id
 * @property \Illuminate\Support\Carbon|null $created_at
 * @property \Illuminate\Support\Carbon|null $updated_at
 * @property-read \Illuminate\Database\Eloquent\Collection|\App\Models\Song[] $song
 * @property-read int|null $song_count
 * @method static \Illuminate\Database\Eloquent\Builder|Playlist newModelQuery()
 * @method static \Illuminate\Database\Eloquent\Builder|Playlist newQuery()
 * @method static \Illuminate\Database\Eloquent\Builder|Playlist query()
 * @method static \Illuminate\Database\Eloquent\Builder|Playlist whereCreatedAt($value)
 * @method static \Illuminate\Database\Eloquent\Builder|Playlist whereId($value)
 * @method static \Illuminate\Database\Eloquent\Builder|Playlist whereName($value)
 * @method static \Illuminate\Database\Eloquent\Builder|Playlist wherePicture($value)
 * @method static \Illuminate\Database\Eloquent\Builder|Playlist whereUpdatedAt($value)
 * @method static \Illuminate\Database\Eloquent\Builder|Playlist whereUserId($value)
 * @mixin \Eloquent
 */
class Playlist extends Model
{
    use HasFactory;

    protected $fillable = [
        'name',
        'picture',
        'user_id'
    ];

    public function song()
    {
        return $this->hasMany(Song::class, 'song_id');
    }

    public static function createPlaylist(int $id, string $name, string $picture): bool
    {
        $isCreated = Playlist::create(
            [
            'user_id' => $id,
            'name' => $name,
            'picture' => $picture
            ]
        );

        if (!$isCreated) {
            return false;
        }

        return true;
    }

    public static function searchPlaylist(int $id, string $input): Collection
    {
        return self::where('user_id', '=', $id)
            ->where('name', 'like', '%' . $input . '%')->get();
    }
}
