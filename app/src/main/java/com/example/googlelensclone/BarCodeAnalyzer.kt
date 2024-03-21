package com.example.googlelensclone

import android.content.Context
import android.os.Handler
import android.widget.Toast
import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage

class BarCodeAnalyzer(private val applicationContext: Context) : ImageAnalysis.Analyzer {
    private val scanner = BarcodeScanning.getClient()

    @OptIn(ExperimentalGetImage::class)
    override fun analyze(imageProxy: ImageProxy) {
        imageProxy.image?.let {
            val inputImage = InputImage.fromMediaImage(
                it,
                imageProxy.imageInfo.rotationDegrees
            )
            scanner.process(inputImage)
                .addOnSuccessListener { codes ->
                    val message = buildString {
                        codes.forEach { barcode ->
                            append("Format=${barcode.format}\nValue=${barcode.rawValue}\n")
                        }
                    }
                    // Show toast with barcode information
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
//Teena