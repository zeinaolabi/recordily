<?php

namespace App\Http\Controllers;

use App\Http\Requests\LiveEventRequest;
use App\Http\Requests\MessageRequest;
use App\Models\LiveEvent;
use App\Models\Message;
use Illuminate\Contracts\Auth\Factory;
use Illuminate\Http\JsonResponse;
use Illuminate\Support\Facades\Auth;

class LiveEventController extends Controller
{
    public function __construct(
        private readonly Factory $authManager,
    ) {
    }

    public function addLiveEvent(LiveEventRequest $request): JsonResponse
    {
        $id = $this->authManager->guard()->id();

        $created = LiveEvent::addLiveEvent($request->get("name"), $request->get("firebase_id"), $id);

        if (!$created) {
            return response()->json("Failed to add live event", 400);
        }

        return response()->json("Live Event Created", 201);
    }

    public function addMessage(MessageRequest $request): JsonResponse
    {
        $id = Auth::id();

        $created = Message::addMessage($request->get("message"), $request->get("live_event_id"), $id);

        if (!$created) {
            return response()->json("Failed to add message", 400);
        }

        return response()->json("Message Created", 201);
    }
}
