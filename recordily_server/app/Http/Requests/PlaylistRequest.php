<?php

namespace App\Http\Requests;

use Illuminate\Foundation\Http\FormRequest;

class PlaylistRequest extends FormRequest
{
    public function all($keys = null)
    {
        $request['playlist_id'] = $this->route('playlist_id');
        $request['song_id'] = $this->route('song_id');
        $request['limit'] = $this->route('limit');
        return $request;
    }

    public function authorize()
    {
        return true;
    }

    public function rules()
    {
        return [
            'playlist_id' => 'required|exists:playlists,id'
        ];
    }
}
