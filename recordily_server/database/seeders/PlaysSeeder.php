<?php

namespace Database\Seeders;

use App\Models\Play;
use Illuminate\Database\Console\Seeds\WithoutModelEvents;
use Illuminate\Database\Seeder;

class PlaysSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        Play::create([
            'user_id' => 1,
            'song_id' => 1
        ]);

        Play::create([
            'user_id' => 1,
            'song_id' => 1
        ]);

        Play::create([
            'user_id' => 2,
            'song_id' => 10
        ]);

        Play::create([
            'user_id' => 2,
            'song_id' => 10
        ]);

        Play::create([
            'user_id' => 2,
            'song_id' => 10
        ]);

        Play::create([
            'user_id' => 1,
            'song_id' => 2
        ]);

        Play::create([
            'user_id' => 1,
            'song_id' => 2
        ]);

        Play::create([
            'user_id' => 1,
            'song_id' => 12
        ]);

        Play::create([
            'user_id' => 1,
            'song_id' => 3
        ]);

        Play::create([
            'user_id' => 1,
            'song_id' => 13
        ]);
        Play::create([
            'user_id' => 1,
            'song_id' => 4
        ]);

        Play::create([
            'user_id' => 1,
            'song_id' => 5
        ]);
    }
}
