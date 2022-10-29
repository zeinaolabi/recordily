package com.example.recordily_client.view_models

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Activity
import android.app.Application
import android.content.pm.PackageManager
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.net.toUri
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.NonCancellable.start
import java.io.IOException
import java.io.File
import android.media.AudioManager
import android.media.MediaPlayer.OnPreparedListener
import java.io.FileNotFoundException

import java.io.FileInputStream
import java.security.AccessController.getContext
import java.io.FileDescriptor





@SuppressLint("StaticFieldLeak")
class RecordViewModel(application: Application): AndroidViewModel(application) {

    private val context = getApplication<Application>().applicationContext
    private var mediaRecorder: MediaRecorder? = null
    private var state: Boolean = false
    private var recordingStopped: Boolean = false
    private var currentFile: File? = null

    @RequiresApi(Build.VERSION_CODES.S)
    fun recordAudio(){
        if(ActivityCompat.checkSelfPermission(context, android.Manifest.permission.RECORD_AUDIO)  != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(
                context as Activity, arrayOf(
                    android.Manifest.permission.RECORD_AUDIO,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                111)
        }
        else{
            startRecording()
        }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun startRecording(){
        mediaRecorder = MediaRecorder()

        val dir = File(Environment.getExternalStorageDirectory().absolutePath)

        val fileName = System.currentTimeMillis().toString() + ".mp3"
        val file = File(dir, fileName)

        try{
            if (!dir.isDirectory) {
                dir.mkdir()
            }
            file.createNewFile()
            currentFile = file
        } catch (e: Exception) {
            Log.i("Exception", e.message.toString())
        }

        mediaRecorder?.setAudioSource(MediaRecorder.AudioSource.MIC)
        mediaRecorder?.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
        mediaRecorder?.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
        mediaRecorder?.setOutputFile(file)

        try {
            mediaRecorder?.prepare()
            mediaRecorder?.start()
            state = true
        } catch (e: IllegalStateException) {
            Log.i("IllegalStateException", e.message.toString())
        } catch (e: IOException) {
            Log.i("IOException", e.message.toString())
        }
    }

    fun stopRecording(){
        if(state){
            mediaRecorder?.stop()
            mediaRecorder?.reset()
            mediaRecorder?.release()
            state = false
            Toast.makeText(context, "Recording Saved", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(context, "You are not recording right now!", Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("RestrictedApi", "SetTextI18n")
    @TargetApi(Build.VERSION_CODES.N)
    fun pauseRecording() {
        if(state) {
            if(!recordingStopped){
                mediaRecorder?.pause()
                recordingStopped = true
            }else{
                resumeRecording()
            }
        }
    }

    @SuppressLint("RestrictedApi", "SetTextI18n")
    @TargetApi(Build.VERSION_CODES.N)
    fun resumeRecording() {
        mediaRecorder?.resume()
        recordingStopped = false
    }

    @SuppressLint("RestrictedApi", "SetTextI18n")
    @TargetApi(Build.VERSION_CODES.N)
    fun deleteRecording() {
        mediaRecorder?.stop()
        mediaRecorder?.release()
        recordingStopped = false
        currentFile?.delete()
    }

    fun playContentUri() {

        val path = currentFile?.path.toString()
        Log.i("path2", currentFile?.path.toString())
//        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)


        val mediaPlayer = MediaPlayer.create(context, Uri.parse(path))
        try {

            mediaPlayer.start()
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (t: Throwable) {
            t.printStackTrace()
        }

    }

}
