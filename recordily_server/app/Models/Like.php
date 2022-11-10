<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Support\Collection;

class Like extends BaseModel
{
    use HasFactory;

    protected $fillable = [
        'user_id',
        'song_id'
    ];

    public function song()
    {
        return $this->belongsTo(Song::class, 'song_id');
    }

    public static function searchLikedSongs(int $id, string $input): Collection
    {
        return Song::whereIn(
            'id',
            function ($query) use ($id) {
                $query
                    ->select('song_id')
                    ->from(with(new Like())->getTable())
                    ->where('user_id', $id);
            }
        )->where('name', 'like', '%' . $input . '%')->get();
    }

    public static function checkIfLiked(int $id, int $song_id): bool
    {
        $isLiked = self::where('user_id', $id)
            ->where('song_id', $song_id)->get();

        if ($isLiked->isEmpty()) {
            return false;
        }

        return true;
    }

    public static function likeSong(int $id, int $song_id): self
    {
        return self::create([
            'user_id' => $id,
            'song_id' => $song_id
        ]);
    }

    public static function unlikeSong(int $id, int $song_id): int
    {
        return self::where('user_id', $id)
            ->where('song_id', $song_id)
            ->delete();
    }
}
