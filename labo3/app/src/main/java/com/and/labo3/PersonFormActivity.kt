package com.and.labo3

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class PersonFormActivity : AppCompatActivity() {

    /**
     * Path of the image saved when taking the selfie.
     */
    lateinit var selfiePath: String

    /**
     * Reference on the image view to display selfie
     */
    lateinit var selfieView : ImageView

    /**
     * Request code given to intent when taking selfie
     */
    private val REQUEST_IMAGE_CAPTURE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_person_form)

        selfieView = findViewById(R.id.additional_picture_image)

        selfieView.setOnClickListener {
            dispatchTakePictureIntent()
        }
    }


    /**
     * Create a file to store the selfie in the application storage
     * Source: https://developer.android.com/training/camera/photobasics?authuser=1
     */
    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File = getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg",               /* suffix */
            storageDir                  /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            selfiePath = absolutePath
        }
    }

    /**
     * Create a file to store the selfie in the application storage
     * Source: https://developer.android.com/training/camera/photobasics?authuser=1
     */
    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->

            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(packageManager)?.also {

                // Create the File where the photo should go
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    null
                }

                // Continue only if the File was successfully created
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        this,
                        "com.and.labo3.fileprovider",
                        it
                    )

                    // Launching photo capture intent.
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                }
            }
        }
    }

    /**
     * Handling intent result.
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        // In case of photo capture
        if(requestCode == REQUEST_IMAGE_CAPTURE) {

            // Rotate the photo correctly in a coroutine
            // Source: https://www.geeksforgeeks.org/what-is-exifinterface-in-android/
            lifecycleScope.launch {

                val originalSelfieFile = File(selfiePath)
                val bitmapToDisplay : Bitmap?

                if(originalSelfieFile.exists()){

                    val originalBitmap = BitmapFactory.decodeFile(originalSelfieFile.absolutePath);
                    val exifInterface = ExifInterface(originalSelfieFile)
                    val selfieRotation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                                                                       ExifInterface.ORIENTATION_UNDEFINED)
                    val selfieRotationInDegrees: Int = exifToDegrees(selfieRotation)

                    // Making rotation matrix
                    val matrix = Matrix()
                    if (selfieRotation != 0) {
                        matrix.postRotate(selfieRotationInDegrees.toFloat())
                    }

                    // Rotating to a new bitmap
                    bitmapToDisplay = Bitmap.createBitmap(originalBitmap, 0, 0, originalBitmap.width, originalBitmap.height, matrix, false)

                } else {
                    bitmapToDisplay = null
                }

                // Layout update
                withContext(Dispatchers.Main) {
                    if(bitmapToDisplay == null) {
                        selfieView.setImageResource(R.drawable.placeholder_selfie)
                    } else {
                        selfieView.setImageBitmap(bitmapToDisplay)
                    }
                }
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    /**
     * Extract the rotation in degress from elif constant
     * Source: http://www.java2s.com/example/android/graphics/convert-exif-to-degrees.html
     */
    private fun exifToDegrees(exifOrientation: Int): Int {
        val rotation: Int = when (exifOrientation) {
            ExifInterface.ORIENTATION_ROTATE_90, ExifInterface.ORIENTATION_TRANSPOSE -> 90 /*from   w ww  .  j a va  2s .c om*/
            ExifInterface.ORIENTATION_ROTATE_180, ExifInterface.ORIENTATION_FLIP_VERTICAL -> 180
            ExifInterface.ORIENTATION_ROTATE_270, ExifInterface.ORIENTATION_TRANSVERSE -> 270
            else -> 0
        }
        return rotation
    }
}