<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    public function up()
    {
        Schema::create('follows', function (Blueprint $table) {
            $table->id();
            $table->foreignId('follower_id');
            $table->foreignId('followed_id');
            $table->timestamps();
            $table->unique(['follower_id','followed_id']);
        });
    }

    public function down()
    {
        Schema::dropIfExists('follows');
    }
};
