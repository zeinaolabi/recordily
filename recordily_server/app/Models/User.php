<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Foundation\Auth\User as Authenticatable;
use Illuminate\Notifications\Notifiable;
use Illuminate\Support\Collection;
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
        'password'
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

    public function follows()
    {
        return $this->HasMany(Follow::class, 'follower_id')->select('followed_id');
    }

    public function albums()
    {
        return $this->HasMany(Album::class, 'user_id');
    }

    public function songs()
    {
        return $this->HasMany(Song::class, 'user_id');
    }

    public static function getFollowedArtists(int $id): Collection
    {
        $artistTypeID = 0;

        return self::whereIn(
            'id',
            self::find($id)->follows
        )
            ->where('user_type_id', $artistTypeID)
            ->get();
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
        $artistTypeID = 0;
        $user = User::find($id);

        if ($user->user_type_id = $artistTypeID) {
            return false;
        }

        return true;
    }

    public static function searchForArtist(string $input): Collection
    {
        $artistTypeID = 0;

        return self::where('name', 'like', '%' . $input . '%')
            ->where('user_type_id', $artistTypeID)
            ->get();
    }

    public static function getArtistAlbums(int $id, int $limit): Collection
    {
        return self::find($id)->albums
            ->where('is_published', 1)->values()
            ->slice(0, $limit);
    }

    public static function getArtistSongs(int $id, int $limit): Collection
    {
        return self::find($id)->songs
            ->where('is_published', 1)
            ->values()->slice(0, $limit);
    }
}
