<?php

use Illuminate\Support\Facades\Route;

Route::get('/', function () {
    return view('reset_password');
});

Route::get('/reset_password', function () {
    return view('reset_password');
});
