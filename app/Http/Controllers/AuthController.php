<?php
namespace App\Http\Controllers;
use App\Http\Requests\LoginRequest;
use Illuminate\Auth\AuthManager;
use Illuminate\Auth\Events\Login;
use Illuminate\Contracts\Auth\Guard;
use Illuminate\Contracts\Auth\StatefulGuard;
use Illuminate\Http\Request;
use App\Models\User;
use Illuminate\Support\Facades\Hash;

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

    public function register(Request $request) {
        //Validate all input
//        $validator = Validator::make($request->all(), [
//            'name' => 'required|string|between:2,100',
//            'email' => 'required|string|email|max:100|unique:users',
//            'password' => 'required|string|min:6',
//        ]);

        //If validation failed, display an error
//        if($validator->fails()){
//            return response()->json($validator->errors()->toJson(), 400);
//        }

        $hashed = Hash::make($request->password);
        //Create a new user with a hashed password
        $user = User::create(['email' => $request->email,
            'password' => $hashed,
            'salt' => $request->salt,
            'user_type_id' => $request->user_type_id]);

//        $user->token = auth()->login($user);

        return response()->json([
            'message' => 'User successfully registered',
            'user' => $user
        ], 201);
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
