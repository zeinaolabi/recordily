<?php

namespace App\Jobs;

use App\Models\Song;
use Illuminate\Bus\Queueable;
use Illuminate\Contracts\Auth\Factory;
use Illuminate\Contracts\Queue\ShouldQueue;
use Illuminate\Contracts\Routing\UrlGenerator;
use Illuminate\Filesystem\Filesystem;
use Illuminate\Filesystem\FilesystemManager;
use Illuminate\Foundation\Bus\Dispatchable;
use Illuminate\Http\UploadedFile;
use Illuminate\Queue\InteractsWithQueue;
use Illuminate\Queue\SerializesModels;
use Illuminate\Support\Facades\File;
use Illuminate\Support\Facades\Storage;

class ProcessSong implements ShouldQueue
{
    use Dispatchable;
    use InteractsWithQueue;
    use Queueable;
    use SerializesModels;

    public int $id;
    public array $metadata;
    public string $song;
    public string $picture;
    public string $pictureExtension;

    public function __construct(
        int $id,
        array $metadata,
        string $song,
        string $picture,
        string $pictureExtension
    ) {
        $this->id = $id;
        $this->metadata = $metadata;
        $this->song = $song;
        $this->picture = $picture;
        $this->pictureExtension = $pictureExtension;
    }


    public function handle(Filesystem $filesystem, FilesystemManager $filesystemManager)
    {

        $path = public_path() . '/uploads/' . $this->id . '/';
        $songPath = $path . $this->metadata['song_id'] . '/';

        if (!$filesystem->exists($path)) {
            $filesystem->makeDirectory($path);
        }

        if (!$filesystem->exists($songPath)) {
            $filesystem->makeDirectory($songPath);
        }

        file_put_contents($songPath . $this->metadata['chunk_num'], base64_decode($this->song));

        $chunks = $filesystemManager->disk('uploads')->files($this->id . '/' . $this->metadata['song_id'] . '/');

        if (count($chunks) == $this->metadata['chunks_size']) {
            for ($i = 0; $i < count($chunks); $i++) {
                $contents = file_get_contents($songPath . $i);
                file_put_contents($songPath . $this->metadata['song_id'] . '.mp3', $contents, FILE_APPEND);
                $filesystem->delete($songPath . $i);
            }

            $picturePath = '/images/' . $this->id . '/' . uniqid() . '.' . $this->pictureExtension;
            file_put_contents(public_path() . $picturePath, base64_decode($this->picture));

            $size = $filesystem->size($songPath . $this->metadata['song_id'] . '.mp3');

            $albumID = array_key_exists("album_id", $this->metadata) ? $this->metadata['album_id'] : null;

            $songSavedPath = '/uploads/' . $this->id . '/' . $this->metadata['song_id'] . '/'
                . $this->metadata['song_id'] . '.mp3';

            Song::createSong($this->metadata['name'], $picturePath, $songSavedPath, $size, $this->id, $albumID);
        }
    }
}
