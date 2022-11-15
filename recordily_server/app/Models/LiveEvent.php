<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;
use phpDocumentor\Reflection\Types\Boolean;

class LiveEvent extends Model
{
    use HasFactory;

    protected $fillable = [
        'name',
        'user_id',
        'firebase_id'
    ];

    public static function addLiveEvent(string $name, string $firebaseID, int $id): Boolean
    {
        return self::create(
            [
            'name' => $name,
            'firebase_id' => $firebaseID,
            'user_id' => $id
            ]
        );
    }
}
