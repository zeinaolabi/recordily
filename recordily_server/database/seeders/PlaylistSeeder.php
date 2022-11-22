<?php

namespace Database\Seeders;

use App\Models\Playlist;
use App\Models\PlaylistHasSong;
use Illuminate\Database\Console\Seeds\WithoutModelEvents;
use Illuminate\Database\Seeder;

class PlaylistSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        Playlist::create([
            'id' => 1,
            'name' => 'Fav Pink Floyd Songs',
            'picture' => 'images/1/6377c4fa652a2.jpg',
            'user_id' => 2
        ]);

        Playlist::create([
            'id' => 2,
            'name' => 'Night Songs',
            'picture' => 'images/2/6377de6a73659.jpg',
            'user_id' => 2
        ]);

        PlaylistHasSong::addToPlaylist(1, 1);
        PlaylistHasSong::addToPlaylist(1, 2);
        PlaylistHasSong::addToPlaylist(1, 3);
        PlaylistHasSong::addToPlaylist(1, 4);
        PlaylistHasSong::addToPlaylist(1, 5);
        PlaylistHasSong::addToPlaylist(2, 5);
        PlaylistHasSong::addToPlaylist(2, 7);

    }
}
