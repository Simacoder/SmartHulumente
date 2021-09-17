package com.example.smarthulumente

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.MediaStore.Images.Media.insertImage
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso

class TakePictureActivity2 : AppCompatActivity() {
    private val REQUEST_CODE = 100
    private lateinit var imageView: ImageView
    var imgBird: ImageView? = null
    //var sharePic1: Button = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_take_picture2)

        imgBird = findViewById(R.id.imageBird)
        val sharePic1: Button  = findViewById<Button>(R.id.sharePic)

        sharePic1.setOnClickListener{
            val b = BitmapFactory.decodeResource(resources, R.drawable.simanga)
            val intent=Intent().apply {
                intent.action=Intent.ACTION_SEND

                val path= insertImage(contentResolver,b, "Title", null)
                val uri=Uri.parse(path)

                intent.putExtra(Intent.EXTRA_STREAM,uri)
                intent.type="image/*"

            }
            startActivity(Intent.createChooser(intent,"Share to: "))

        }


        val button = findViewById<Button>(R.id.picture)
        imageView = findViewById(R.id.imageView)
        button.setOnClickListener() {
            this.capturePhoto()
        }
    }



    private fun capturePhoto(){
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, REQUEST_CODE)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK &&  requestCode == REQUEST_CODE && data != null){
            imageView.setImageBitmap(data.extras?.get("data") as Bitmap)
        }
    }
}