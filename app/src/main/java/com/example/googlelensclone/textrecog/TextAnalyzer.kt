package com.example.googlelensclone.textrecog

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.widget.Toast
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition

import com.google.mlkit.vision.text.latin.TextRecognizerOptions

class TextAnalyzer(private val applicationContext: Context) : ImageAnalysis.Analyzer {
    private val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

    @SuppressLint("UnsafeOptInUsageError")
    override fun analyze(imageProxy: ImageProxy) {
        imageProxy.image?.let {
            val inputImage = InputImage.fromMediaImage(
                it,
                imageProxy.imageInfo.rotationDegrees
            )
            recognizer.process(inputImage)
                .addOnSuccessListener { text ->
                    val message = buildString {
                        text.textBlocks.forEach { block ->
                            append("LINES=${block.lines.joinToString("\n") { it.text }}\n")
                        }
                    }
                    // Show toast with recognized text
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
