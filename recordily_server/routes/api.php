<?php

use App\Http\Controllers\AuthController;
use App\Http\Controllers\ForgotPasswordController;
use App\Http\Controllers\SongController;
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
Route::post('forgot_password', [ForgotPasswordController::class, "forgotPassword"]);
Route::get('/reset-password/{token}',[ForgotPasswordController::class, "resetPassword"])->name('password.reset');;
Route::post('upload_song',[SongController::class, "uploadSong"]);
Route::get('get_songs',[SongController::class, "getSongs"]);
Route::get('play_song',[SongController::class, "playSong"]);
Route::get('top_played_songs',[SongController::class, "getTopPlayedSongs"]);
