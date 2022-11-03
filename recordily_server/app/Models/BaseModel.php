<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Support\Collection;
use Illuminate\Support\Facades\DB;

abstract class BaseModel extends Model
{
    use HasFactory;

    public static function getTopSongs(string $credentials, int $limit, string $order_by): Collection{
        return self::select('song_id', DB::raw('count(*) as ' . $credentials ))
            ->groupBy('song_id')
            ->orderBy($order_by, 'desc')
            ->limit($limit)
            ->pluck('song_id');
    }
}
