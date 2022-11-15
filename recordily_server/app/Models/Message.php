<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;
use phpDocumentor\Reflection\Types\Boolean;

class Message extends Model
{
    use HasFactory;

    protected $fillable = [
        'live_event_id',
        'message',
        'user_id'
    ];

    public static function addMessage(string $message, string $liveEventID, int $id): Boolean
    {
        return self::create(
            [
                'message' => $message,
                'live_event_id' => $liveEventID,
                'user_id' => $id
            ]
        );
    }
}
