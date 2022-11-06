<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Support\Collection;

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
