package com.example.recordily_client.view_models

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Application
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Build
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import java.io.IOException
import java.io.File

@SuppressLint("StaticFieldLeak")
class RecordViewModel(application: Application): AndroidViewModel(application) {

    private val context = getApplication<Application>().applicationContext
    private var mediaRecorder: MediaRecorder? = null
    private var state: Boolean = false
    private var currentFile: File? = null

    @SuppressLint("WrongConstant")
    @RequiresApi(Build.VERSION_CODES.S)
     fun startRecording(){
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
        }else{
            Toast.makeText(context, "You are not recording right now!", Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("RestrictedApi", "SetTextI18n")
    @TargetApi(Build.VERSION_CODES.N)
    fun deleteRecording() {
        currentFile?.delete()
    }

    fun playRecordContent() {
        val path = currentFile?.path.toString()

        val mediaPlayer = MediaPlayer()
        try {
            mediaPlayer.setDataSource(path)
            mediaPlayer.prepareAsync()
            mediaPlayer.start()
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (t: Throwable) {
            t.printStackTrace()
        }
    }
}
