<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    public function up()
    {
        Schema::create('likes', function (Blueprint $table) {
            $table->id();
            $table->foreignId('user_id');
            $table->foreignId('song_id');
            $table->timestamps();
            $table->unique( array('user_id','song_id') );
        });
    }

    public function down()
    {
        Schema::dropIfExists('likes');
    }
};