<?php

namespace App\Http\Requests;

use Illuminate\Foundation\Http\FormRequest;

class SongRequest extends FormRequest
{
    public function all($keys = null)
    {
        $request['song_id'] = $this->route('song_id');
        return $request;
    }

    public function authorize()
    {
        return true;
    }

    public function rules()
    {
        return [
            'song_id' => 'required|exists:songs,id'
        ];
    }
}
