<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Http\JsonResponse;
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
        return self::create(
            [
                'user_id' => $id,
                'name' => $name,
                'picture' => $picture
            ]
        );
    }

    public static function publishAlbum(int $album_id): JsonResponse
    {
        $album = Album::find($album_id);

        if($album === null){
            return response()->json("Album not found");
        }

        $published = Album::where('id', $album_id)->update(['is_published' => 1]);

        if ($published === null) {
            return response()->json("Unsuccessful publish attempt");
        }

        $publish_songs = Song::where('album_id', $album_id)->update(['is_published' => 1]);

        if ($publish_songs === null) {
            Album::where('id', $album_id)->update(['is_published' => 0]);
            return response()->json("Unsuccessful publish attempt");
        }

        return response()->json("Successfully published");
    }
}
