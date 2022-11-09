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

    public static function findPublished(int $id): object
    {
        $is_published = 1;

        return self::where('id', $id)
            ->where('is_published', $is_published)
            ->first();
    }

    public static function getArtistUnreleasedAlbums(int $id, int $limit): Collection
    {
        $not_published = 0;

        return self::where('is_published', $not_published)
            ->where('user_id', $id)
            ->limit($limit)
            ->get();
    }

    public static function createAlbum(int $id, string $name, string $picture): bool
    {
        $isCreated = self::create(
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
}
