package com.example.lasertarget

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import org.opencv.android.OpenCVLoader

class MainActivity : AppCompatActivity() {
    private lateinit var textViewStatus: TextView
    private var isOpenCvInit = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        textViewStatus = findViewById(R.id.textViewStatus)
        isOpenCvInit = OpenCVLoader.initLocal()
        updateControls()

    }
    private fun updateControls() {
        if(isOpenCvInit){
            textViewStatus.text = "OpenCV initialized"
        } else {
            textViewStatus.text = "OpenCV failed"
        }
    }
}