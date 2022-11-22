<?php

namespace Database\Seeders;

use App\Models\Follow;
use Illuminate\Database\Console\Seeds\WithoutModelEvents;
use Illuminate\Database\Seeder;

class FollowSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        Follow::create([
            'follower_id' => 2,
            'followed_id' => 1
        ]);

        Follow::create([
            'follower_id' => 2,
            'followed_id' => 3
        ]);
    }
}
