package com.example.recordily_client.view_models

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Activity
import android.app.Application
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.os.Build
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.lifecycle.AndroidViewModel
import java.io.IOException
import java.io.File




@SuppressLint("StaticFieldLeak")
class RecordViewModel(application: Application): AndroidViewModel(application) {

    private var mediaRecorder: MediaRecorder? = null
    private var state: Boolean = false
    private var recordingStopped: Boolean = false

    @RequiresApi(Build.VERSION_CODES.S)
    fun recordAudio(){
        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.RECORD_AUDIO)  != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(
                this, arrayOf(
                    android.Manifest.permission.RECORD_AUDIO,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                111)
        }
        else{
            startRecording()
        }
    }


}