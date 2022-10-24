<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    public function up()
    {
        Schema::create('playlist_has_songs', function (Blueprint $table) {
            $table->id();
            $table->foreignId('playlist_id');
            $table->foreignId('song_id');
            $table->timestamps();
            $table->unique( array('playlist_id','song_id') );
        });
    }

    public function down()
    {
        Schema::dropIfExists('playlist_has_songs');
    }
};
