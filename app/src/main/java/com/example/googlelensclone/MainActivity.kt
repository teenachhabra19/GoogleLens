package com.example.googlelensclone

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import com.example.googlelensclone.FaceDetect.FaceDetectActivity
import com.example.googlelensclone.imageLabeler.ImageLabelingActivity
import com.example.googlelensclone.textrecog.TextRecognitionActivity

class MainActivity : AppCompatActivity() {
    lateinit var btnTakeExtPhoto:Button
    lateinit var imgThumb:ImageView
    lateinit var btnCameraActivity:Button
    lateinit var btnBarcodeActivity:Button
    lateinit var btnFaceDetectActivity:Button
    lateinit var btnImgLabelActivity:Button
    lateinit var btnTextRecogActivity: Button
    companion object{
        @JvmStatic val PHOTO_REQ_CODE=234
        @JvmStatic val EXTRA_DATA="data"
    }
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnTakeExtPhoto=findViewById(R.id.btnTakeExtPhoto)
        btnCameraActivity=findViewById(R.id.btnCameraActivity)
        imgThumb=findViewById(R.id.imgThumb)
        btnBarcodeActivity=findViewById(R.id.btnBarcodeActivity)
        btnFaceDetectActivity=findViewById(R.id.btnFaceDetectActivity)
        btnImgLabelActivity=findViewById(R.id.btnImgLabelActivity)
        btnTextRecogActivity=findViewById(R.id.btnTextRecogActivity)
        btnTakeExtPhoto.setOnClickListener {
            val takePhotoIntent= Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(takePhotoIntent, PHOTO_REQ_CODE)
        }
        btnCameraActivity.setOnClickListener {
            startActivity(Intent(this, CameraActivity::class.java))
        }
        btnBarcodeActivity.setOnClickListener {
            startActivity(Intent(this,BarCodeActivity::class.java))
        }
        btnFaceDetectActivity.setOnClickListener {
            startActivity(Intent(this,FaceDetectActivity::class.java))
        }
        btnImgLabelActivity.setOnClickListener {
            startActivity(Intent(this,ImageLabelingActivity::class.java))
        }
        btnTextRecogActivity.setOnClickListener {
            startActivity(Intent(this,TextRecognitionActivity::class.java))
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode== PHOTO_REQ_CODE){
            val bitmap=data?.extras?.get(EXTRA_DATA) as Bitmap
            imgThumb.setImageBitmap(bitmap)
            return
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}