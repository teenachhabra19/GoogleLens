package com.example.googlelensclone

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat

class BarCodeActivity : BaseLensActivity() {
    override val imageAnalyzer=BarCodeAnalyzer()
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