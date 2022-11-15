<?php

namespace App\Http\Controllers;

use App\Http\Requests\LoginRequest;
use App\Http\Requests\RegisterRequest;
use Illuminate\Auth\AuthManager;
use App\Models\User;
use Illuminate\Http\JsonResponse;

class AuthController extends Controller
{
    public function __construct(private readonly AuthManager $auth)
    {
    }

    public function login(LoginRequest $request): JsonResponse
    {
        $isAuthorized = $this->auth->attempt($request->all(['email', 'password']));

        if (!$isAuthorized) {
            return response()->json(['error' => 'Unauthorized'], 401);
        }

        return $this->createNewToken($isAuthorized);
    }

    public function register(RegisterRequest $request): JsonResponse
    {
        $user = User::createUser(
            $request->get('email'),
            $request->get('password'),
            $request->get('user_type_id')
        );

        $user->token = $this->auth->login($user);

        return response()->json($user, 201);
    }

    protected function createNewToken(string $token): JsonResponse
    {
        return response()->json([
                "id" => $this->auth->user()->id,
                "user_type_id" => $this->auth->user()->user_type_id,
                "token" => $token
            ]);
    }
}
