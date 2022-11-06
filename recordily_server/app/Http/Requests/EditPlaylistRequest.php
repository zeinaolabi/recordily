<?php

namespace App\Http\Requests;

use Illuminate\Foundation\Http\FormRequest;

class EditPlaylistRequest extends FormRequest
{
    public function authorize()
    {
        return true;
    }

    public function rules()
    {
        return [
            'name' => 'string',
            'picture' => 'file'
        ];
    }
}
