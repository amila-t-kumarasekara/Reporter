package com.example.reportascene

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.content.FileProvider
import kotlinx.android.synthetic.main.activity_report.*
import java.io.File

private const val FILE_NAME = "phpto.jpg"
private const val REQUEST_CODE = 42
private lateinit var photoFile: File
class Report : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report)

        // Open the camera and take picture

        btnTakePicture.setOnClickListener {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            photoFile = getPhotoFile(FILE_NAME)

            // This DOESNT work for API >= 24
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT , photoFile)
            val fileprovider = FileProvider.getUriForFile(this,"com.example.reportascene.fileprovider",photoFile)
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT ,fileprovider)
            if(takePictureIntent.resolveActivity(this.packageManager) !=null ) {
                startActivityForResult(takePictureIntent , REQUEST_CODE)
            }
            else{
                Toast.makeText(this, "UNABLE TO OPEN CAMERA", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun getPhotoFile(fileName: String): File {
        // Use "getExternalFileDir"
        val storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(fileName , "jpg" , storageDirectory)


    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val takenImage = data?.extras?.get("data") as Bitmap
            imageView.setImageBitmap(takenImage)
        }
        else{
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}
