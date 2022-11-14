<?php

namespace App\Http\Requests;

use Illuminate\Foundation\Http\FormRequest;

class LiveEventRequest extends FormRequest
{
    public function authorize()
    {
        return true;
    }

    public function rules()
    {
        return [
            'name' => 'string|required'
        ];
    }
}
