<?php

namespace Database\Seeders;

use App\Models\User;
use Hash;
use Illuminate\Database\Console\Seeds\WithoutModelEvents;
use Illuminate\Database\Seeder;

class UserSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        User::create([
            'id' => 1,
            'name' => "Guns N' Roses",
            'email' => 'gunsnroses@gmail.com',
            'password' => Hash::make('test123'),
            'profile_picture' => 'images/2/6377cfc0afd2b.jpg',
            'user_type_id' => 0
        ]);

        User::create([
            'id' => 2,
            'name' => "Pink Floyd",
            'email' => 'pinkfloyd@gmail.com',
            'password' => Hash::make('test123'),
            'profile_picture' => 'images/1/6377c289959bd.jpg',
            'user_type_id' => 0
        ]);

        User::create([
            'id' => 3,
            'name' => "The Beatles",
            'email' => 'beatles@gmail.com',
            'password' => Hash::make('test123'),
            'profile_picture' => 'images/3/6377d30243e7e.jpg',
            'user_type_id' => 0
        ]);
    }
}
