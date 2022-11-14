<?php

namespace App\Http\Controllers;

use App\Http\Requests\LiveEventRequest;
use App\Models\LiveEvent;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;

class LiveEventController extends Controller
{
    public function addLiveEvent(LiveEventRequest $request)
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
}
