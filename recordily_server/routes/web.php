<?php

use Illuminate\Support\Facades\Route;

Route::get('/reset_password', function () {
    return view('reset_password');
});
