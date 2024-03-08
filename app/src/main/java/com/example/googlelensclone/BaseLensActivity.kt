package com.example.googlelensclone

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

abstract class BaseLensActivity:AppCompatActivity() {
    companion object{
        @JvmStatic
        val CAMERA_PERM_CODE=422
    }
    lateinit var previewViewBarcode: PreviewView
    lateinit var btnStartScanner: Button
    abstract val imageAnalyzer:ImageAnalysis.Analyzer
    protected lateinit var imageAnalysis:ImageAnalysis
    private fun askCameraPermission(){
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE),
            CAMERA_PERM_CODE
        )
    }
    private fun startCamera(){
        val cameraProviderFuture= ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener(
            Runnable{
                val cameraProvider=cameraProviderFuture.get()

                val preview = Preview.Builder().build().also {
                    it.setSurfaceProvider(previewViewBarcode.surfaceProvider)
                }
                imageAnalysis= ImageAnalysis.Builder().build()
                val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
                try {
                    cameraProvider.unbindAll()
                    cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageAnalysis)
                } catch (ex: Exception) {
                    Log.e("CAM", "Error bindind camera", ex)
                }

            }, ContextCompat.getMainExecutor(this)
        )
    }
    abstract fun startScanner()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lens)
        previewViewBarcode=findViewById(R.id.previewBarcode)
        btnStartScanner=findViewById(R.id.btnStartScanner)
        askCameraPermission()
        btnStartScanner.setOnClickListener {
            startScanner()
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == CAMERA_PERM_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startCamera()
            } else {
                AlertDialog.Builder(this)
                    .setTitle("Permission Error")
                    .setMessage("Camera Permission not provided")
                    .setPositiveButton("OK") { _, _ -> finish() }
                    .setCancelable(false)
                    .show()
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}