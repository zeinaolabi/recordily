<?php

namespace App\Http\Requests;

use Illuminate\Foundation\Http\FormRequest;
use Symfony\Component\Console\Input\Input;

class AlbumRequest extends FormRequest
{
    public function all($keys = null)
    {
        $request['album_id'] = $this->route('album_id');
        return $request;
    }

    public function authorize()
    {
        return true;
    }

    public function rules()
    {
        return [
            'album_id' => 'required|exists:albums,id'
        ];
    }
}
