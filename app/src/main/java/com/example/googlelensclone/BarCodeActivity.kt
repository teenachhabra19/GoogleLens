package com.example.googlelensclone

import android.content.Context
import androidx.appcompat.app.AlertDialog
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.core.content.ContextCompat

class BarCodeActivity : BaseLensActivity() {
    override val imageAnalyzer: BarCodeAnalyzer by lazy {
        BarCodeAnalyzer(applicationContext)
    }

    override fun startScanner() {
        scanBarcode()
    }

    private fun scanBarcode() {
        imageAnalysis.setAnalyzer(
            ContextCompat.getMainExecutor(this),
            imageAnalyzer
        )
    }
}
