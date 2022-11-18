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

    public function songs()
    {
        return $this->hasMany(Song::class, 'album_id');
    }

    public static function getArtistUnreleasedAlbums(int $id, int $limit): Collection
    {
        return self::where('is_published', 0)
            ->where('user_id', $id)
            ->limit($limit)
            ->get();
    }

    public static function createAlbum(int $id, string $name, string $picture): self
    {
        return self::create(
            [
                'user_id' => $id,
                'name' => $name,
                'picture' => $picture
            ]
        );
    }

    public static function publishAlbum(int $albumID): JsonResponse
    {
        $published = Album::where('id', $albumID)->update(['is_published' => 1]);

        if ($published === null) {
            return response()->json("Unsuccessful publish attempt");
        }

        $publish_songs = Song::where('album_id', $albumID)->update(['is_published' => 1]);

        if ($publish_songs === null) {
            Album::where('id', $albumID)->update(['is_published' => 0]);
            return response()->json("Unsuccessful publish attempt");
        }

        return response()->json("Successfully published");
    }
}
