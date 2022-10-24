<?php

use App\Http\Controllers\AuthController;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;

//Route::middleware('auth:sanctum')->get('/user', function (Request $request) {
//    return $request->user();
//});
//
//Route::group(['middleware' => 'auth:api', 'prefix' => 'auth'], function () {
//    Route::post('register', 'AuthController@logout');
//});

Route::post('login', [AuthController::class, "login"]);
Route::post('register', [AuthController::class, "register"]);
