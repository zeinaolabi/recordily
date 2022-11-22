<?php

namespace App\Http\Controllers;

use App\Models\Album;
use App\Models\Song;
use Illuminate\Http\UploadedFile;

trait InfoTrait
{
    private function imageToURL(Song|Album $data)
    {
        if ($data->picture !== null) {
            $data->picture = $this->urlGenerator->to($data->picture);
        }
        $data->artist_name = $data->user->name;
        unset($data->user);
    }

    private function saveImage(UploadedFile $picture, int $id): bool|string
    {
        $path = public_path() . '/images/' . $id;

        if (!$this->filesystem->exists($path)) {
            $this->filesystem->makeDirectory($path);
        }

        $fileName = uniqid() . '.' . $picture->extension();
        $picturePath = sprintf('/%s', $id);

        $pictureSaved = $this->filesystemManager->disk('images')
            ->putFileAs($picturePath, $picture, $fileName);

        if (!$pictureSaved) {
            return false;
        }

        return sprintf('/images/%s/%s', $id, $fileName);
    }
}
