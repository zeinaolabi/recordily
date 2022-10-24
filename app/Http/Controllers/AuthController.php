<?php
namespace App\Http\Controllers;
use App\Http\Requests\LoginRequest;
use App\Http\Requests\RegisterRequest;
use Illuminate\Auth\AuthManager;
use App\Models\User;


class AuthController extends Controller
{
    public function __construct(private readonly AuthManager $auth)
    {
    }

    public function login(LoginRequest $request){
        //Check if the user is authenticated
        $isAuthorized = $this->auth->attempt($request->all(['email', 'password']));

        //If authorization failed, display an error
        if (!$isAuthorized) {
            return response()->json(['error' => 'Unauthorized'], 401);
        }

        //Send back a token
        return $this->createNewToken($isAuthorized);
    }

    public function register(RegisterRequest $request) {
        $user = User::createUser(
            $request->get('email'),
            $request->get('password'),
            $request->get('user_type_id')
        );

        $user->token = $this->auth->login($user);

        return response()->json($user,201);
    }

    protected function createNewToken($token){
        return response()->json([
            'access_token' => $token,
            'token_type' => 'bearer',
            'expires_in' => auth()->factory()->getTTL() * 60,
            'user' => auth()->user()
        ]);
    }
}
