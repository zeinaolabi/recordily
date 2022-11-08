<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Support\Collection;
use Illuminate\Support\Facades\DB;

/**
 * App\Models\Song
 *
 * @property int $id
 * @property string $name
 * @property string $picture
 * @property string $path
 * @property int $size
 * @property string $type
 * @property int $time_length
 * @property int $is_published
 * @property int $user_id
 * @property int|null $album_id
 * @property \Illuminate\Support\Carbon|null $created_at
 * @property \Illuminate\Support\Carbon|null $updated_at
 * @property-read \Illuminate\Database\Eloquent\Collection|\App\Models\Like[] $likes
 * @property-read int|null $likes_count
 * @property-read \Illuminate\Database\Eloquent\Collection|\App\Models\Play[] $plays
 * @property-read int|null $plays_count
 * @property-read \App\Models\User|null $user
 * @method static \Illuminate\Database\Eloquent\Builder|Song newModelQuery()
 * @method static \Illuminate\Database\Eloquent\Builder|Song newQuery()
 * @method static \Illuminate\Database\Eloquent\Builder|Song query()
 * @method static \Illuminate\Database\Eloquent\Builder|Song whereAlbumId($value)
 * @method static \Illuminate\Database\Eloquent\Builder|Song whereCreatedAt($value)
 * @method static \Illuminate\Database\Eloquent\Builder|Song whereId($value)
 * @method static \Illuminate\Database\Eloquent\Builder|Song whereIsPublished($value)
 * @method static \Illuminate\Database\Eloquent\Builder|Song whereName($value)
 * @method static \Illuminate\Database\Eloquent\Builder|Song wherePath($value)
 * @method static \Illuminate\Database\Eloquent\Builder|Song wherePicture($value)
 * @method static \Illuminate\Database\Eloquent\Builder|Song whereSize($value)
 * @method static \Illuminate\Database\Eloquent\Builder|Song whereTimeLength($value)
 * @method static \Illuminate\Database\Eloquent\Builder|Song whereType($value)
 * @method static \Illuminate\Database\Eloquent\Builder|Song whereUpdatedAt($value)
 * @method static \Illuminate\Database\Eloquent\Builder|Song whereUserId($value)
 * @mixin \Eloquent
 */
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

    public function likes()
    {
        return $this->hasMany(Like::class, 'user_id');
    }

    public function plays()
    {
        return $this->hasMany(Play::class, 'user_id');
    }

    public function user()
    {
        return $this->belongsTo(User::class, 'user_id')->select('name');
    }

    public static function getArtistSongs(int $artist_id, int $limit): Collection
    {
        $is_published = 1;

        return self::where('user_id', $artist_id)
            ->where('is_published', $is_published)
            ->limit($limit)
            ->get();
    }

    public static function getArtistTopSongs(int $limit, int $artist_id): Collection
    {
        return self::select('song_id', DB::raw('count(*) as plays'))
            ->join('plays', 'songs.id', 'song_id')
            ->where('songs.user_id', $artist_id)
            ->groupBy('song_id')
            ->orderBy('plays', 'desc')
            ->limit($limit)
            ->pluck('song_id');
    }

    public static function getArtistUnreleasedSongs(int $id, int $limit): Collection
    {
        $not_published = 0;

        return self::where('is_published', $not_published)
            ->where('user_id', $id)
            ->limit($limit)
            ->get();
    }

    public static function searchForSong(string $input)
    {
        $is_published = 1;

        return self::where('is_published', $is_published)->
        where('name', 'like', '%' . $input . '%')
            ->get();
    }
}
