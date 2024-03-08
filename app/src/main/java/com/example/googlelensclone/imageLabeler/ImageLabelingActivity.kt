package com.example.googlelensclone.imageLabeler

import androidx.camera.core.ImageAnalysis
import androidx.core.content.ContextCompat
import com.example.googlelensclone.BaseLensActivity
import com.google.mlkit.vision.label.ImageLabel

class ImageLabelingActivity: BaseLensActivity() {
    override val imageAnalyzer= ImageLabelAnalyzer()


    override fun startScanner() {
          startImageLabeling()
    }
    private fun startImageLabeling(){
        imageAnalysis.setAnalyzer(
            ContextCompat.getMainExecutor(this),
            imageAnalyzer
        )
    }
}