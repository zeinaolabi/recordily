<?php

namespace Database\Seeders;

use App\Models\Song;
use Illuminate\Database\Seeder;
use Illuminate\Support\Facades\File;

class SongSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        Song::create([
            'id' => 1,
            'name' => 'Comfortably Numb',
            'picture' => 'images/1/6377c4fa652a2.jpg',
            'path' => 'uploads/1/16687935927551/16687935927551.mp3',
            'size' => File::size(public_path() . '/uploads/1/16687935927551/16687935927551.mp3'),
            'is_published' => 1,
            'user_id' => 2,
            'album_id' => 1
        ]);

        Song::create([
            'id' => 2,
            'name' => 'Hey You',
            'picture' => 'images/1/6377c4fa652a2.jpg',
            'path' => 'uploads/1/16687937932501/16687937932501.mp3',
            'size' => File::size(public_path() . '/uploads/1/16687937932501/16687937932501.mp3'),
            'is_published' => 1,
            'user_id' => 2,
            'album_id' => 1
        ]);

        Song::create([
            'id' => 3,
            'name' => 'Mother',
            'picture' => 'images/1/6377c4fa652a2.jpg',
            'path' => 'uploads/1/16687938654771/16687938654771.mp3',
            'size' => File::size(public_path() . '/uploads/1/16687938654771/16687938654771.mp3'),
            'is_published' => 1,
            'user_id' => 2,
            'album_id' => 1
        ]);

        Song::create([
            'id' => 4,
            'name' => 'Nobody Home',
            'picture' => 'images/1/6377c4fa652a2.jpg',
            'path' => 'uploads/1/16687939238311/16687939238311.mp3',
            'size' => File::size(public_path() . '/uploads/1/16687939238311/16687939238311.mp3'),
            'is_published' => 1,
            'user_id' => 2,
            'album_id' => 1
        ]);

        Song::create([
            'id' => 5,
            'name' => 'Brain Damage',
            'picture' => 'images/1/6377c8a193944.png',
            'path' => 'uploads/1/16687944756971/16687944756971.mp3',
            'size' => File::size(public_path() . '/uploads/1/16687944756971/16687944756971.mp3'),
            'is_published' => 1,
            'user_id' => 2,
            'album_id' => 2
        ]);

        Song::create([
            'id' => 6,
            'name' => 'Breathe (In the Air)',
            'picture' => 'images/1/6377c8a193944.png',
            'path' => 'uploads/1/16687945277771/16687945277771.mp3',
            'size' => File::size(public_path() . '/uploads/1/16687945277771/16687945277771.mp3'),
            'is_published' => 1,
            'user_id' => 2,
            'album_id' => 2
        ]);

        Song::create([
            'id' => 7,
            'name' => 'Us and Them',
            'picture' => 'images/1/6377c8a193944.png',
            'path' => 'uploads/1/16687946269411/16687946269411.mp3',
            'size' => File::size(public_path() . '/uploads/1/16687946269411/16687946269411.mp3'),
            'is_published' => 1,
            'user_id' => 2,
            'album_id' => 2
        ]);

        Song::create([
            'id' => 8,
            'name' => 'Eclipse',
            'picture' => 'images/1/6377c8a193944.png',
            'path' => 'uploads/1/16687947303851/16687947303851.mp3',
            'size' => File::size(public_path() . '/uploads/1/16687947303851/16687947303851.mp3'),
            'is_published' => 1,
            'user_id' => 2,
            'album_id' => 2
        ]);

        Song::create([
            'id' => 9,
            'name' => 'Pigs',
            'picture' => 'images/1/6377ca631ab4c.jpg',
            'path' => 'uploads/1/16687952472951/16687952472951.mp3',
            'size' => File::size(public_path() . '/uploads/1/16687952472951/16687952472951.mp3'),
            'is_published' => 1,
            'user_id' => 2,
            'album_id' => 2
        ]);

        Song::create([
            'id' => 10,
            'name' => 'Goodbye Cruel World',
            'picture' => 'images/1/6377c4fa652a2.jpg',
            'path' => 'uploads/1/16688006181331/16688006181331.mp3',
            'size' => File::size(public_path() . '/uploads/1/16688006181331/16688006181331.mp3'),
            'is_published' => 1,
            'user_id' => 2,
            'album_id' => 1
        ]);

        Song::create([
            'id' => 11,
            'name' => 'Welcome To the Jungle',
            'picture' => 'images/2/6377cfef86f8d.jpg',
            'path' => 'uploads/2/16687964311812/16687964311812.mp3',
            'size' => File::size(public_path() . '/uploads/2/16687964311812/16687964311812.mp3'),
            'is_published' => 1,
            'user_id' => 1,
            'album_id' => 3
        ]);

        Song::create([
            'id' => 12,
            'name' => 'November Rain',
            'picture' => 'images/2/6377d0c80417a.jpg',
            'path' => 'uploads/2/16687966141072/16687966141072.mp3',
            'size' => File::size(public_path() . '/uploads/2/16687966141072/16687966141072.mp3'),
            'is_published' => 1,
            'user_id' => 1,
            'album_id' => 3
        ]);

        Song::create([
            'id' => 13,
            'name' => 'Live and Let Die',
            'picture' => 'images/2/6377d0c80417a.jpg',
            'path' => 'uploads/2/16687967978842/16687967978842.mp3',
            'size' => File::size(public_path() . '/uploads/2/16687967978842/16687967978842.mp3'),
            'is_published' => 1,
            'user_id' => 1,
            'album_id' => 3
        ]);

        Song::create([
            'id' => 14,
            'name' => "Knockin' on Heaven's Door",
            'picture' => 'images/2/637a0b8d08b56.jpg',
            'path' => 'uploads/2/16687977798832/16687977798832.mp3',
            'size' => File::size(public_path() . '/uploads/2/16687977798832/16687977798832.mp3'),
            'is_published' => 1,
            'user_id' => 1,
            'album_id' => 4
        ]);

        Song::create([
            'id' => 15,
            'name' => 'Yesterdays',
            'picture' => 'images/2/637a0b8d08b56.jpg',
            'path' => 'uploads/2/16687979077912/16687979077912.mp3',
            'size' => File::size(public_path() . '/uploads/2/16687979077912/16687979077912.mp3'),
            'is_published' => 1,
            'user_id' => 1,
            'album_id' => 4
        ]);

        Song::create([
            'id' => 16,
            'name' => 'Let It Be',
            'picture' => 'images/1/6377d30243e7e.jpg',
            'path' => 'uploads/3/16687972215693/16687972215693.mp3',
            'size' => File::size(public_path() . '/uploads/3/16687972215693/16687972215693.mp3'),
            'is_published' => 1,
            'user_id' => 3,
            'album_id' => null
        ]);
    }
}
