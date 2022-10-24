<?php
namespace App\Http\Controllers;
use App\Http\Requests\LoginRequest;
use Illuminate\Contracts\Auth\Guard;
use Illuminate\Contracts\Auth\StatefulGuard;
use Illuminate\Http\Request;
use App\Models\User;
use Illuminate\Support\Facades\Hash;
use Validator;

class AuthController extends Controller
{
    public function __construct(private readonly StatefulGuard $guard)
    {
    }

    public function login(LoginRequest $request){

        dd(get_class(auth()));
        $token = auth()->attempt($request->all(['email, password']));
        //If authorization failed, display an error
        if (! $token) {
            return response()->json(['error' => 'Unauthorized'], 401);
        }

        //Send back a token
        return $this->createNewToken($token);
    }

    public function register(Request $request) {
        //Validate all input
        $validator = Validator::make($request->all(), [
            'name' => 'required|string|between:2,100',
            'email' => 'required|string|email|max:100|unique:users',
            'password' => 'required|string|min:6',
        ]);

        //If validation failed, display an error
        if($validator->fails()){
            return response()->json($validator->errors()->toJson(), 400);
        }

        //Create a new user with a hashed password
        $user = User::create(array_merge(
            $validator->validated(),
            ['password' => Hash::make($request->get('password'))]
        ));

        $user->token = auth()->login($user);

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
