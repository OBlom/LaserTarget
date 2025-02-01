package com.example.lasertarget

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import org.opencv.android.OpenCVLoader
import org.opencv.android.CameraBridgeViewBase
import org.opencv.core.CvType
import org.opencv.core.Mat
import android.Manifest
import android.content.pm.PackageManager
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


class MainActivity : AppCompatActivity(),CameraBridgeViewBase.CvCameraViewListener2 {
    private lateinit var buttonStartPreview: Button
    private lateinit var buttonStopPreview: Button
    private lateinit var checkBoxProcessing: CheckBox
    private lateinit var imageView: ImageView
    private lateinit var openCvCameraView: CameraBridgeViewBase
    private var isPreviewActive = false

    private lateinit var textViewStatus: TextView
    private var isOpenCvInit = false
    private val cameraPermissionRqCode = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        textViewStatus = findViewById(R.id.textViewStatus)
        buttonStartPreview = findViewById(R.id.buttonStartPreview)
        buttonStopPreview = findViewById(R.id.buttonStopPreview)
        checkBoxProcessing = findViewById(R.id.checkboxEnableProcessing)
        imageView = findViewById(R.id.imageView)
        openCvCameraView = findViewById(R.id.cameraView)
        isOpenCvInit = OpenCVLoader.initLocal()

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) !=
            PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                cameraPermissionRqCode
            )
        }
        openCvCameraView.setCameraIndex(0)
        openCvCameraView.setCvCameraViewListener(this)
        buttonStartPreview.setOnClickListener {
            openCvCameraView.setCameraPermissionGranted()
            openCvCameraView.enableView()
            updateControls()
        }

        buttonStopPreview.setOnClickListener {
            openCvCameraView.disableView()
            updateControls()
        }
        updateControls()

    }

    override fun onCameraViewStarted(width: Int, height: Int) {
        isPreviewActive = true
        updateControls()
    }

    override fun onCameraViewStopped() {
        isPreviewActive = false
        updateControls()
    }

    override fun onCameraFrame(inputFrame: CameraBridgeViewBase.CvCameraViewFrame?): Mat {
        return inputFrame!!.rgba()
    }
    private fun updateControls() {
        if(isOpenCvInit){
            textViewStatus.text = "OpenCV initialized"

            buttonStartPreview.isEnabled = !isPreviewActive
            buttonStopPreview.isEnabled = isPreviewActive
        } else {
            textViewStatus.text = "OpenCV failed"
            buttonStartPreview.isEnabled = false
            buttonStopPreview.isEnabled = false
        }
    }
}