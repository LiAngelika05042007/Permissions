package com.angelika.permissions.ui.activity

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity

import com.angelika.permissions.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val pickImage = 100
    private var imageUrl: Uri? = null

    //    private val galleryLauncher =
//        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
//            binding.image.setImageURI(uri)
//        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListener()
    }

    private fun setupListener() {
        binding.btnOpenGallery.setOnClickListener {
            when {
                checkSelfPermission(READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED -> {
                    val intent = Intent(Intent.ACTION_PICK)
                    intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
                    startActivityForResult(intent, pickImage)
//                    galleryLauncher.launch("image/*")
                }

                shouldShowRequestPermissionRationale(READ_EXTERNAL_STORAGE) -> {

                }

                else -> {
                    requestPermissions(
                        arrayOf(READ_EXTERNAL_STORAGE),
                        100
                    )
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickImage){
            imageUrl = data?.data
            binding.image.setImageURI(imageUrl)
        }
    }
}