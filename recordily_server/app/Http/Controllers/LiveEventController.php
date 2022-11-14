<?php

namespace App\Http\Controllers;

use App\Http\Requests\LiveEventRequest;
use App\Http\Requests\MessageRequest;
use App\Models\LiveEvent;
use App\Models\Message;
use Illuminate\Http\JsonResponse;
use Illuminate\Support\Facades\Auth;

class LiveEventController extends Controller
{
    public function addLiveEvent(LiveEventRequest $request): JsonResponse
    {
        $id = Auth::id();

        $created = LiveEvent::create([
            'name' => $request->get("name"),
            'user_id' => $id
        ]);

        if(!$created){
            return response()->json("Failed to add live event", 400);
        }

        return response()->json("Live Event Created", 201);
    }
    public function addMessage(MessageRequest $request): JsonResponse
    {
        $id = Auth::id();

        $created = Message::create([
            'message' => $request->get("message"),
            'user_id' => $id,
            'live_event_id' => $request->get("live_event_id")
        ]);

        if(!$created){
            return response()->json("Failed to add message", 400);
        }

        return response()->json("Message Created", 201);
    }
}
