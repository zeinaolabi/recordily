<?php

namespace Database\Seeders;

use App\Models\Like;
use Illuminate\Database\Console\Seeds\WithoutModelEvents;
use Illuminate\Database\Seeder;

class LikeSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        Like::create([
            'user_id' => 1,
            'song_id' => 1
        ]);

        Like::create([
            'user_id' => 1,
            'song_id' => 8
        ]);

        Like::create([
            'user_id' => 1,
            'song_id' => 9
        ]);

        Like::create([
            'user_id' => 1,
            'song_id' => 12
        ]);

        Like::create([
            'user_id' => 1,
            'song_id' => 5
        ]);

        Like::create([
            'user_id' => 1,
            'song_id' => 6
        ]);

        Like::create([
            'user_id' => 1,
            'song_id' => 7
        ]);

        Like::create([
            'user_id' => 2,
            'song_id' => 1
        ]);

        Like::create([
            'user_id' => 2,
            'song_id' => 12
        ]);

        Like::create([
            'user_id' => 2,
            'song_id' => 10
        ]);
    }
}
