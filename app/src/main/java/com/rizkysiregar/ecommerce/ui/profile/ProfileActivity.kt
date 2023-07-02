package com.rizkysiregar.ecommerce.ui.profile

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.text.Html
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.core.widget.doOnTextChanged
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.rizkysiregar.ecommerce.MainActivity
import com.rizkysiregar.ecommerce.R
import com.rizkysiregar.ecommerce.data.local.preference.PreferenceManager
import com.rizkysiregar.ecommerce.data.model.ProfileModel
import com.rizkysiregar.ecommerce.databinding.ActivityProfileBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.io.FileOutputStream

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private val profileViewModel: ProfileViewModel by viewModel()

    companion object {
        private const val CAMERA_PERMISSION_REQUEST_CODE = 100
    }

    private var image: Uri? = null

    private val cameraLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val imageBitmap = result.data?.extras?.get("data") as Bitmap?
                image = result.data?.data
                Glide.with(this)
                    .load(imageBitmap)
                    .apply(RequestOptions.circleCropTransform())
                    .into(binding.imgProfile)
            }
        }

    private val galleryLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val imageUri = result.data?.data
                image = imageUri
                Glide.with(this)
                    .load(imageUri)
                    .apply(RequestOptions.circleCropTransform())
                    .into(binding.imgProfile)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        coloredText()

        binding.btnProfile.setOnClickListener {
            try {
                val edtUserName = binding.edtName.text.toString()
                val userImageUri = image
                val userImageFile = convertUriToFile(this, userImageUri!!)
                val provideModel = ProfileModel(edtUserName, userImageFile)
                // call profile viewmodel
                profileViewModel.postProfile(provideModel)
                profileViewModel.data.observe(this) {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    if (it.code == 200) {
                        PreferenceManager.setIsLogin(this, true)
                        navigateToHome()
                    } else {
                        Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                Toast.makeText(this, "Error Image; $e", Toast.LENGTH_SHORT).show()
            }
        }

        binding.imgProfile.setOnClickListener {
            showImageSourceDialog()
        }

        binding.textInputLayout.boxStrokeColor =
            ContextCompat.getColor(this@ProfileActivity, R.color.indicator_filled)

        binding.edtName.doOnTextChanged { text, start, before, count ->
            if (text != null) {
                binding.btnProfile.isEnabled = !text.isEmpty()
            }
        }
    }

    private fun showImageSourceDialog() {
        val options = arrayOf("Gallery", "Camera")
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Choose Image Source")
        builder.setItems(options) { _, which ->
            when (which) {
                0 -> openGallery()
                1 -> openCamera()
            }
        }
        builder.show()
    }

    private fun openGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        galleryLauncher.launch(galleryIntent)
    }

    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (hasCameraPermission()) {
            cameraLauncher.launch(cameraIntent)
        } else {
            requestCameraPermission()
        }
    }

    private fun hasCameraPermission(): Boolean {
        return checkSelfPermission(android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestCameraPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(
                arrayOf(android.Manifest.permission.CAMERA),
                CAMERA_PERMISSION_REQUEST_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE && grantResults.isNotEmpty() &&
            grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            openCamera()
        }
    }

    private fun convertUriToFile(context: Context, uri: Uri): File? {
        val fileName = getFileName(context, uri)
        val outputFile = File(context.cacheDir, fileName)

        try {
            val inputStream = context.contentResolver.openInputStream(uri)
            FileOutputStream(outputFile).use { outputStream ->
                inputStream?.copyTo(outputStream)
            }
            return outputFile
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    private fun getFileName(context: Context, uri: Uri): String? {
        var fileName: String? = null
        context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
            if (cursor.moveToFirst()) {
                val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                fileName = cursor.getString(nameIndex)
            }
        }
        return fileName
    }
    private fun coloredText() {
        val coloredText = getString(R.string.terms_and_conditions_login)
        binding.tvBottomBtnDoneProfile.text = HtmlCompat.fromHtml(coloredText,
            HtmlCompat.FROM_HTML_MODE_LEGACY
        )
    }

    private fun navigateToHome() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}


