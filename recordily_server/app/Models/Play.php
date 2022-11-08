<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Support\Collection;
use Illuminate\Support\Facades\DB;

/**
 * App\Models\Play
 *
 * @property int $id
 * @property int $user_id
 * @property int $song_id
 * @property \Illuminate\Support\Carbon|null $created_at
 * @property \Illuminate\Support\Carbon|null $updated_at
 * @property-read \App\Models\Song|null $song
 * @method static \Illuminate\Database\Eloquent\Builder|Play newModelQuery()
 * @method static \Illuminate\Database\Eloquent\Builder|Play newQuery()
 * @method static \Illuminate\Database\Eloquent\Builder|Play query()
 * @method static \Illuminate\Database\Eloquent\Builder|Play whereCreatedAt($value)
 * @method static \Illuminate\Database\Eloquent\Builder|Play whereId($value)
 * @method static \Illuminate\Database\Eloquent\Builder|Play whereSongId($value)
 * @method static \Illuminate\Database\Eloquent\Builder|Play whereUpdatedAt($value)
 * @method static \Illuminate\Database\Eloquent\Builder|Play whereUserId($value)
 * @mixin \Eloquent
 */
class Play extends BaseModel
{
    use HasFactory;

    protected $fillable = [
        'user_id',
        'song_id'
    ];

    public static function getRecentlyPlayed(int $id, int $limit): Collection
    {
        return self::select('song_id')
            ->where('user_id', $id)
            ->orderBy('created_at', 'desc')
            ->limit($limit)
            ->pluck('song_id');
    }

    public static function getUserTopSongs(int $id, int $limit): Collection
    {
        return self::select('song_id', DB::raw('count(*) as plays'))
            ->groupBy('song_id')
            ->where('user_id', $id)
            ->orderBy('plays', 'desc')
            ->limit($limit)
            ->pluck('song_id');
    }


    public function song()
    {
        return $this->belongsTo(Song::class, 'song_id');
    }
}
