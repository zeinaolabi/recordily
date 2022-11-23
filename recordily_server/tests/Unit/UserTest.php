<?php

namespace Tests\Unit;

use App\Models\Song;
use App\Models\User;
use Tests\TestCase;
use Illuminate\Foundation\Testing\RefreshDatabase;
use Tymon\JWTAuth\Facades\JWTAuth;

class UserTest extends TestCase
{
    use RefreshDatabase;

    public function test_unauthorization()
    {
        $this->seed();

        $response = $this->post('/api/login', [
            'email' => 'not_registered@gmail.com',
            'password' => 'test123'
        ]);

        $response->assertUnauthorized();
    }

    public function test_registration()
    {
        $this->seed();

        $response = $this->post('/api/register', [
            'email' => 'new_user@gmail.com',
            'password' => 'test123',
            'user_type_id' => 1
        ]);

        $response->assertStatus(201);
    }

    public function test_user_can_get_liked_songs()
    {
        $this->seed();

        $token = JWTAuth::fromUser(User::take(1)->first());
        $response = $this->get('/api/auth/liked_songs', ['Authorization' => "Bearer $token"]);
        $response->assertStatus(200);
    }

    public function test_check_if_song_is_liked()
    {
        $this->seed();

        $token = JWTAuth::fromUser(User::take(1)->first());
        $songID = Song::take(1)->first()->id;
        $response = $this->get('/api/auth/is_liked/'. $songID, ['Authorization' => "Bearer $token"]);
        $response->assertStatus(200);
    }

    public function test_get_song()
    {
        $this->seed();

        $token = JWTAuth::fromUser(User::take(1)->first());
        $songID = Song::take(1)->first()->id;
        $response = $this->get('/api/auth/get_song/'.$songID, ['Authorization' => "Bearer $token"]);
        $response->assertStatus(200);
    }

    public function test_create_new_user()
    {
        $this->post('/api/register', [
            'email' => 'john_doe@gmail.com',
            'password' => 'test123',
            'user_type_id' => 1
        ]);

        $this->assertDatabaseHas('users', [
            'email' => 'john_doe@gmail.com'
        ]);
    }
}
