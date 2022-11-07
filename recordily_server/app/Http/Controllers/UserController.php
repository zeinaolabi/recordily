<?php

namespace App\Http\Controllers;

use App\Models\User;
use Illuminate\Http\JsonResponse;
use Illuminate\Support\Facades\Auth;

class UserController extends Controller
{
   public function getUserInfo(): JsonResponse
   {
       $id = Auth::id();

       $user = User::find($id);

       return response()->json($user);
   }
}
