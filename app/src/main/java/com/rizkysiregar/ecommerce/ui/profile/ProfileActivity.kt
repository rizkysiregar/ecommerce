package com.rizkysiregar.ecommerce.ui.profile

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.FileProvider
import com.rizkysiregar.ecommerce.MainActivity
import com.rizkysiregar.ecommerce.R
import com.rizkysiregar.ecommerce.databinding.ActivityProfileBinding
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private val REQUEST_PICK_IMAGE = 1
    private val REQUEST_IMAGE_CAPTURE = 1
    private var currentPhotoPath: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnProfile.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        binding.imgProfile.setOnClickListener {
            showDialogProfile()
        }
    }

    private fun showDialogProfile() {
        // init object
        val builder = AlertDialog.Builder(this)
        val items = arrayOf("Camera", "Galeri")

        // set dialog
        builder.setTitle("Change Profile")
            .setItems(items){dialog: DialogInterface, which: Int ->
                val selectedItem = items[which]
                dialog.dismiss()
                Toast.makeText(this, selectedItem, Toast.LENGTH_SHORT).show()

                if (selectedItem == "Galeri"){
                    openGallery()
                }else{
//                    openCamera()
                }
            }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*" // To open only images, change to "video/*" for videos
        startActivityForResult(intent, REQUEST_PICK_IMAGE)
    }

    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        // Create the file where the photo should be saved
        val photoFile: File? = try {
            createImageFile()
        } catch (ex: Exception) {
            null
        }

        // Continue only if the file was successfully created
        photoFile?.also {
            val photoURI = FileProvider.getUriForFile(
                this,
                "com.your.package.name.fileprovider",
                it
            )
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
        }
    }

    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        ).apply {
            currentPhotoPath = absolutePath
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            // Handle the captured image here
            // The image file path is stored in 'currentPhotoPath'
            // Do something with the captured image
        }
    }

}