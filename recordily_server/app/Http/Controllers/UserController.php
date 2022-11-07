<?php

namespace App\Http\Controllers;

use App\Models\User;
use Exception;
use Illuminate\Http\JsonResponse;
use Illuminate\Support\Facades\Auth;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\URL;

class UserController extends Controller
{
   public function getUserInfo(): JsonResponse
   {
       $id = Auth::id();

       $user = User::find($id);

       return response()->json($user);
   }

    public function editProfile(Request $request): JsonResponse
    {
        $id = Auth::id();

        $user = User::find($id);

        if ($request->file('profile_picture')) {
            try {
                $picture = $request->file('profile_picture');

                $picture_path = '/images/' . $id . '/' . uniqid() . '.' . $picture->extension();
                file_put_contents(public_path() . $picture_path, $picture->getContent());

                $user->picture = $picture_path;
            } catch (Exception $e) {
                return response()->json(['error' => $e], 400);
            }
        }

        $user->name = $request->get('name') ? str_replace('"', '', $request->get('name')) : $user->name;

        if (!$user->save()) {
            return response()->json('unsuccessfully attempt', 400);
        }

        return response()->json('successfully edited', 201);
    }
}
