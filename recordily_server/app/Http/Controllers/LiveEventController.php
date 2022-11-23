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

        LiveEvent::addLiveEvent($request->get("name"), $request->get("firebase_id"), $id);

        return response()->json("Live Event Created", 201);
    }

    public function addMessage(MessageRequest $request): JsonResponse
    {
        $id = $this->authManager->guard()->id();

        Message::addMessage($request->get("message"), $request->get("live_event_id"), $id);

        return response()->json("Message Created", 201);
    }
}
