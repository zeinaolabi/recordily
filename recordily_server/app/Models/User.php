<?php

namespace App\Models;

// use Illuminate\Contracts\Auth\MustVerifyEmail;
use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Foundation\Auth\User as Authenticatable;
use Illuminate\Notifications\Notifiable;
use Illuminate\Support\Facades\Hash;
use Laravel\Sanctum\HasApiTokens;
use Tymon\JWTAuth\Contracts\JWTSubject;

class User extends Authenticatable implements JWTSubject
{
    use HasApiTokens;
    use HasFactory;
    use Notifiable;

    protected $fillable = [
        'name',
        'email',
        'password',
        'biography',
        'profile_picture',
        'user_type_id'
    ];

    protected $hidden = [
        'password',
        'remember_token',
    ];

    protected $casts = [
        'email_verified_at' => 'datetime',
    ];

    public function getJWTIdentifier()
    {
        return $this->getKey();
    }

    public function getJWTCustomClaims()
    {
        return [];
    }

    public static function createUser($email, $password, $type): User
    {
        $hashed = Hash::make($password);

        return User::create(
            [
            'email' => $email,
            'password' => $hashed,
            'user_type_id' => $type
            ]
        );
    }

    public static function isArtist(int $id): bool
    {
        $artist_type_id = 0;
        $user = User::find($id);

        if($user->user_type_id = $artist_type_id){
            return false;
        }

        return true;
    }
}
