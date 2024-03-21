package com.example.googlelensclone.FaceDetect

import android.os.Bundle
import androidx.camera.core.ImageAnalysis
import androidx.core.content.ContextCompat
import com.example.googlelensclone.BaseLensActivity

class FaceDetectActivity : BaseLensActivity() {
    override val imageAnalyzer: FaceDetectAnalyzer by lazy {
        FaceDetectAnalyzer(applicationContext)
    }

    override fun startScanner() {
        startFaceDetect()
    }

    private fun startFaceDetect() {
        imageAnalysis.setAnalyzer(
            ContextCompat.getMainExecutor(this),
            imageAnalyzer
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}
