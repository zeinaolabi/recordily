<?php

namespace App\Http\Requests;

use Illuminate\Foundation\Http\FormRequest;

class ArtistRequest extends FormRequest
{
    public function all($keys = null)
    {
        $request['artist_id'] = $this->route('artist_id');
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
            'artist_id' => 'required|exists:users,id',
        ];
    }
}
