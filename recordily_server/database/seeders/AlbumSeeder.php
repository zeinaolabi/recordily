<?php

namespace Database\Seeders;

use App\Models\Album;
use Illuminate\Database\Console\Seeds\WithoutModelEvents;
use Illuminate\Database\Seeder;

class AlbumSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        Album::create([
            'id' => 1,
            'name' => 'The Wall',
            'picture' => 'images/1/6377c4fa652a2.jpg',
            'is_published' => 1,
            'user_id' => 2
        ]);

        Album::create([
            'id' => 2,
            'name' => 'Dark Side Of the Moon',
            'picture' => 'images/1/6377c8a193944.png',
            'is_published' => 1,
            'user_id' => 2
        ]);

        Album::create([
            'id' => 3,
            'name' => 'Animals',
            'picture' => 'images/1/6377ca631ab4c.jpg',
            'is_published' => 1,
            'user_id' => 2
        ]);

        Album::create([
            'id' => 4,
            'name' => 'Shattered Illusion',
            'picture' => 'images/2/6377cfef86f8d.jpg',
            'is_published' => 1,
            'user_id' => 1
        ]);

        Album::create([
            'id' => 5,
            'name' => 'Use Your Illusion I',
            'picture' => 'images/2/637a0b8d08b56.jpg',
            'is_published' => 1,
            'user_id' => 1
        ]);

        Album::create([
            'id' => 6,
            'name' => 'Use Your Illusion II',
            'picture' => 'images/2/6377d0c80417a.jpg',
            'is_published' => 1,
            'user_id' => 1
        ]);
    }
}
