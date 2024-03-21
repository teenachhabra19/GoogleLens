package com.example.googlelensclone.imageLabeler

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.widget.Toast
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.label.ImageLabeling
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions

class ImageLabelAnalyzer(private val applicationContext: Context) : ImageAnalysis.Analyzer {
    private val labeler = ImageLabeling.getClient(ImageLabelerOptions.DEFAULT_OPTIONS)

    @SuppressLint("UnsafeOptInUsageError")
    override fun analyze(imageProxy: ImageProxy) {
        imageProxy.image?.let {
            val inputImage = InputImage.fromMediaImage(
                it,
                imageProxy.imageInfo.rotationDegrees
            )
            labeler.process(inputImage)
                .addOnSuccessListener { labels ->
                    val message = buildString {
                        labels.forEach { label ->
                            append("${label.text}: ${label.confidence}\n")
                        }
                    }
                    // Show toast with labels
                    val toast = Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT)
                    toast.show()
                    // Dismiss the toast after 3 seconds
                    Handler().postDelayed({
                        toast.cancel()
                    }, 500)
                }
                .addOnFailureListener { ex ->
                    val errorMessage = "Detection failed: ${ex.message}"
                    val toast = Toast.makeText(applicationContext, errorMessage, Toast.LENGTH_SHORT)
                    toast.show()
                    // Dismiss the toast after 3 seconds
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
