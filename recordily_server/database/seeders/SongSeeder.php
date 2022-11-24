<?php

namespace Database\Seeders;

use App\Models\Song;
use Illuminate\Contracts\Auth\Factory;
use Illuminate\Contracts\Routing\UrlGenerator;
use Illuminate\Database\Seeder;
use Illuminate\Filesystem\Filesystem;
use Illuminate\Filesystem\FilesystemManager;
use Illuminate\Support\Facades\File;

class SongSeeder extends Seeder
{
    public function __construct(
        private readonly Filesystem $filesystem,
    ) {
    }
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
            'path' => 'uploads/test.mp3',
            'size' => $this->filesystem->size(public_path() . '/uploads/test.mp3'),
            'is_published' => 1,
            'user_id' => 2,
            'album_id' => 1
        ]);

        Song::create([
            'id' => 2,
            'name' => 'Hey You',
            'picture' => 'images/1/6377c4fa652a2.jpg',
            'path' => 'uploads/test.mp3',
            'size' => $this->filesystem->size(public_path() . '/uploads/test.mp3'),
            'is_published' => 1,
            'user_id' => 2,
            'album_id' => 1
        ]);

        Song::create([
            'id' => 3,
            'name' => 'Mother',
            'picture' => 'images/1/6377c4fa652a2.jpg',
            'path' => 'uploads/test.mp3',
            'size' => $this->filesystem->size(public_path() . '/uploads/test.mp3'),
            'is_published' => 1,
            'user_id' => 2,
            'album_id' => 1
        ]);

        Song::create([
            'id' => 4,
            'name' => 'Nobody Home',
            'picture' => 'images/1/6377c4fa652a2.jpg',
            'path' => 'uploads/test.mp3',
            'size' => $this->filesystem->size(public_path() . '/uploads/test.mp3'),
            'is_published' => 1,
            'user_id' => 2,
            'album_id' => 1
        ]);

        Song::create([
            'id' => 5,
            'name' => 'Brain Damage',
            'picture' => 'images/1/6377c8a193944.png',
            'path' => 'uploads/test.mp3',
            'size' => $this->filesystem->size(public_path() . '/uploads/test.mp3'),
            'is_published' => 1,
            'user_id' => 2,
            'album_id' => 2
        ]);

        Song::create([
            'id' => 6,
            'name' => 'Breathe (In the Air)',
            'picture' => 'images/1/6377c8a193944.png',
            'path' => 'uploads/test.mp3',
            'size' => $this->filesystem->size(public_path() . '/uploads/test.mp3'),
            'is_published' => 1,
            'user_id' => 2,
            'album_id' => 2
        ]);

        Song::create([
            'id' => 7,
            'name' => 'Us and Them',
            'picture' => 'images/1/6377c8a193944.png',
            'path' => 'uploads/test.mp3',
            'size' => $this->filesystem->size(public_path() . '/uploads/test.mp3'),
            'is_published' => 1,
            'user_id' => 2,
            'album_id' => 2
        ]);

        Song::create([
            'id' => 8,
            'name' => 'Eclipse',
            'picture' => 'images/1/6377c8a193944.png',
            'path' => 'uploads/test.mp3',
            'size' => $this->filesystem->size(public_path() . '/uploads/test.mp3'),
            'is_published' => 1,
            'user_id' => 2,
            'album_id' => 2
        ]);

        Song::create([
            'id' => 9,
            'name' => 'Pigs',
            'picture' => 'images/1/6377ca631ab4c.jpg',
            'path' => 'uploads/test.mp3',
            'size' => $this->filesystem->size(public_path() . '/uploads/test.mp3'),
            'is_published' => 1,
            'user_id' => 2,
            'album_id' => 2
        ]);

        Song::create([
            'id' => 10,
            'name' => 'Goodbye Cruel World',
            'picture' => 'images/1/6377c4fa652a2.jpg',
            'path' => 'uploads/test.mp3',
            'size' => $this->filesystem->size(public_path() . '/uploads/test.mp3'),
            'is_published' => 1,
            'user_id' => 2,
            'album_id' => 1
        ]);

        Song::create([
            'id' => 11,
            'name' => 'Welcome To the Jungle',
            'picture' => 'images/2/6377cfef86f8d.jpg',
            'path' => 'uploads/test.mp3',
            'size' => $this->filesystem->size(public_path() . '/uploads/test.mp3'),
            'is_published' => 1,
            'user_id' => 1,
            'album_id' => 3
        ]);

        Song::create([
            'id' => 12,
            'name' => 'November Rain',
            'picture' => 'images/2/6377d0c80417a.jpg',
            'path' => 'uploads/test.mp3',
            'size' => $this->filesystem->size(public_path() . '/uploads/test.mp3'),
            'is_published' => 1,
            'user_id' => 1,
            'album_id' => 3
        ]);

        Song::create([
            'id' => 13,
            'name' => 'Live and Let Die',
            'picture' => 'images/2/6377d0c80417a.jpg',
            'path' => 'uploads/test.mp3',
            'size' => $this->filesystem->size(public_path() . '/uploads/test.mp3'),
            'is_published' => 1,
            'user_id' => 1,
            'album_id' => 3
        ]);

        Song::create([
            'id' => 14,
            'name' => "Knockin' on Heaven's Door",
            'picture' => 'images/2/637a0b8d08b56.jpg',
            'path' => 'uploads/test.mp3',
            'size' => $this->filesystem->size(public_path() . '/uploads/test.mp3'),
            'is_published' => 1,
            'user_id' => 1,
            'album_id' => 4
        ]);

        Song::create([
            'id' => 15,
            'name' => 'Yesterdays',
            'picture' => 'images/2/637a0b8d08b56.jpg',
            'path' => 'uploads/test.mp3',
            'size' => $this->filesystem->size(public_path() . '/uploads/test.mp3'),
            'is_published' => 1,
            'user_id' => 1,
            'album_id' => 4
        ]);

        Song::create([
            'id' => 16,
            'name' => 'Let It Be',
            'picture' => 'images/3/6377d30243e7e.jpg',
            'path' => 'uploads/test.mp3',
            'size' => $this->filesystem->size(public_path() . '/uploads/test.mp3'),
            'is_published' => 1,
            'user_id' => 3,
            'album_id' => null
        ]);
    }
}
