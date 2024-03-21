package com.example.googlelensclone.FaceDetect

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Handler
import android.widget.Toast
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions

class FaceDetectAnalyzer(private val applicationContext: Context) : ImageAnalysis.Analyzer {
    private val detector = FaceDetection.getClient(
        FaceDetectorOptions.Builder()
            .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
            .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
            .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
            .build()
    )

    @SuppressLint("UnsafeOptInUsageError")
    override fun analyze(imageProxy: ImageProxy) {
        imageProxy.image?.let {
            val inputImage = InputImage.fromMediaImage(
                it,
                imageProxy.imageInfo.rotationDegrees
            )
            detector.process(inputImage)
                .addOnSuccessListener { faces ->
                    val message = "Number of Faces Detected: ${faces.size}"
                    val toast = Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT)
                    toast.show()
                    // Dismiss the toast after 0.5 seconds
                    Handler().postDelayed({
                        toast.cancel()
                    }, 500)
                }
                .addOnFailureListener { ex ->
                    val errorMessage = "Detection failed: ${ex.message}"
                    val toast = Toast.makeText(applicationContext, errorMessage, Toast.LENGTH_SHORT)
                    toast.show()
                    // Dismiss the toast after 0.5 seconds
                    Handler().postDelayed({
                        toast.cancel()
                    }, 500)
                }
                .addOnCompleteListener {
                    imageProxy.close()
                }
        } ?: imageProxy.close()
    }
}
