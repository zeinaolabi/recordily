<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    public function up()
    {
        Schema::create('live_events_has_messages', function (Blueprint $table) {
            $table->id();
            $table->foreignId("live_event_id");
            $table->foreignId("message_id");
            $table->timestamps();
        });
    }

    public function down()
    {
        Schema::dropIfExists('live_events_has_messages');
    }
};
