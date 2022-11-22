<?php

namespace Tests\Unit;

use App\Models\User;
use Tests\TestCase;
use Illuminate\Foundation\Testing\RefreshDatabase;
use Tymon\JWTAuth\Facades\JWTAuth;

class UserTest extends TestCase
{
    use RefreshDatabase;

    public function test_unauthorization()
    {
        $response = $this->post('/api/login', [
            'email' => 'not_registered@gmail.com',
            'password' => 'test123'
        ]);

        $response->assertUnauthorized();
    }

    public function test_registration()
    {
        $response = $this->post('/api/register', [
            'email' => 'new_user@gmail.com',
            'password' => 'test123',
            'user_type_id' => 1
        ]);

        $response->assertStatus(201);
    }

    public function test_user_can_get_liked_songs()
    {
        $token = JWTAuth::fromUser(User::take(1)->first());
        $response = $this->get('/api/auth/liked_songs', ['Authorization' => "Bearer $token"]);
        $response->assertStatus(200);
    }

    public function test_check_if_song_is_liked()
    {
        $token = JWTAuth::fromUser(User::take(1)->first());
        $response = $this->get('/api/auth/is_liked/1', ['Authorization' => "Bearer $token"]);
        $response->assertStatus(200);
    }

    public function test_get_song()
    {
        $token = JWTAuth::fromUser(User::take(1)->first());
        $response = $this->get('/api/auth/get_song/1', ['Authorization' => "Bearer $token"]);
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

    public function test_if_seeders_work()
    {
        $this->seed();
    }
}
