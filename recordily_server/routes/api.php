<?php

use App\Http\Controllers\AuthController;
use App\Http\Controllers\ForgotPasswordController;
use App\Http\Controllers\PlaylistController;
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
Route::get('top_played_songs/{limit}',[SongController::class, "getTopPlayedSongs"]);
Route::get('top_liked_songs/{limit}',[SongController::class, "getTopLikedSongs"]);
Route::get('recently_played_songs/{limit}',[SongController::class, "getRecentlyPlayed"]);
Route::get('suggested_songs/{limit}',[SongController::class, "getSuggestedSongs"]);
Route::get('search/{input}',[SongController::class, "searchForSong"]);
Route::get('liked_songs',[SongController::class, "getLikedSongs"]);
Route::get('like',[SongController::class, "likeSong"]);
Route::get('get_playlists',[PlaylistController::class, "getPlaylists"]);
Route::get('get_playlist_songs/{playlist_id}',[PlaylistController::class, "getPlaylistSongs"]);
Route::post('add_playlist',[PlaylistController::class, "addPlaylist"]);
Route::get('get_playlist/{playlist_id}',[PlaylistController::class, "getPlaylistInfo"]);
Route::post('edit_playlist',[PlaylistController::class, "editPlaylist"]);
