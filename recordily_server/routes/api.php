<?php

use App\Http\Controllers\AlbumController;
use App\Http\Controllers\ArtistController;
use App\Http\Controllers\AuthController;
use App\Http\Controllers\ForgotPasswordController;
use App\Http\Controllers\PlaylistController;
use App\Http\Controllers\SongController;
use App\Http\Controllers\UserController;
use Illuminate\Support\Facades\Route;

Route::group(['middleware' => 'auth:api', 'prefix' => 'auth'], function () {
    Route::controller(ForgotPasswordController::class)->group(
        function () {
        Route::post('forgot_password', "forgotPassword");
        Route::get('/reset-password/{token}', "resetPassword")->name('password.reset');
        }
    );

    Route::controller(SongController::class)->group(
        function () {
            Route::post('upload_song', "uploadSong");
            Route::get('play_song', "playSong");
            Route::get('top_played_songs/{limit}', "getTopPlayedSongs");
            Route::get('top_liked_songs/{limit}', "getTopLikedSongs");
            Route::get('suggested_songs/{limit}', "getSuggestedSongs");
            Route::get('search/{input}',"searchForSong");
            Route::get('liked_songs', "getLikedSongs");
            Route::get('like', "likeSong");
            Route::get('search_liked_songs/{input}', "searchLikedSongs");
            Route::get('unreleased_songs/{limit}',"getUnreleasedSongs");
            Route::get('publish_song/{song_id}',"publishSong");
        }
    );

    Route::controller(PlaylistController::class)->group(
        function () {
            Route::get('get_playlists', "getPlaylists");
            Route::get('get_limited_playlists/{limit}', "getLimitedPlaylists");
            Route::get('get_playlist_songs/{playlist_id}',"getPlaylistSongs");
            Route::post('add_playlist', "addPlaylist");
            Route::get('get_playlist/{playlist_id}',"getPlaylistInfo");
            Route::post('edit_playlist', "editPlaylist");
            Route::get('delete_playlist/{playlist_id}',"deletePlaylist");
            Route::get('search_playlists/{input}', "searchPlaylists");
        }
    );

    Route::controller(ArtistController::class)->group(
        function () {
            Route::get('get_artist_info/{artist_id}',"getArtistInfo");
            Route::get('followed_artists', "getFollowedArtists");
            Route::get('get_artist_followers/{artist_id}', "getArtistFollowers");
            Route::get('is_followed/{artist_id}', "isFollowed");
            Route::get('follow/{artist_id}', "followArtist");
            Route::get('unfollow/{artist_id}', "unfollowArtist");
            Route::get('get_artist_albums/{artist_id}/{limit}', "getArtistAlbums");
            Route::get('get_artist_top_songs/{artist_id}/{limit}', "getArtistTopSongs");
            Route::get('get_artist_songs/{artist_id}/{limit}',"getArtistSongs");
            Route::get('search_followed_artist/{input}',"searchFollowedArtist");
        }
    );

    Route::controller(AlbumController::class)->group(
        function () {
            Route::get('get_album_info/{album_id}',"getAlbum");
            Route::get('get_album_songs/{album_id}',"getAlbumSongs");
            Route::get('unreleased_albums/{limit}',"getUnreleasedAlbums");
            Route::post('create_album',"addAlbum");
            Route::get('publish_album/{limit}',"publishAlbum");
        }
    );

    Route::controller(UserController::class)->group(
        function () {
            Route::get('get_user_info',"getUserInfo");
            Route::get('get_user_top_songs/{limit}',"getUserTopSongs");
            Route::get('recently_played_songs/{limit}',"getRecentlyPlayed");
            Route::post('edit_profile',"editProfile");
        }
    );
});

Route::controller(AuthController::class)->group(
    function () {
        Route::post('login',"login");
        Route::post('register',"register");
    }
);

//Route::get('create_album',[AlbumController::class, "createAlbum"]);
