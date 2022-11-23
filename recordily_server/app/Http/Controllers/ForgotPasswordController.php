<?php

namespace App\Http\Controllers;

use App\Http\Requests\ForgotPasswordRequest;
use App\Http\Requests\LoginRequest;
use App\Models\User;
use Hash;
use Illuminate\Http\JsonResponse;
use Illuminate\Support\Facades\Password;

class ForgotPasswordController extends Controller
{
    public function forgotPassword(ForgotPasswordRequest $request): JsonResponse
    {
        $status = Password::sendResetLink($request->only('email'));

        return $status === Password::RESET_LINK_SENT
            ? response()->json("Email Sent")
            : response()->json("Email Not Sent", 400);
    }

    public function resetPassword(LoginRequest $request): JsonResponse
    {
        $resetPassword = User::where('email', $request->get('email'))
            ->update(['password' => Hash::make($request->get('password'))]);

        if ($resetPassword === null) {
             return response()->json("Resetting Password Failed");
        }

        return response()->json("Password Reset");
    }
}
