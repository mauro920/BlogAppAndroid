package com.example.blogapplication.ui.camera

import android.app.Activity.RESULT_OK
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Camera
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.blogapplication.R
import com.example.blogapplication.core.Resource
import com.example.blogapplication.data.remote.camera.CameraDataSource
import com.example.blogapplication.databinding.FragmentCameraBinding
import com.example.blogapplication.domain.camera.CameraRepoImpl
import com.example.blogapplication.presentation.CameraViewModel
import com.example.blogapplication.presentation.CameraViewModelProvider

class CameraFragment : Fragment(R.layout.fragment_camera) {

    private val REQUEST_IMAGE_CAPTURE = 1
    private lateinit var binding: FragmentCameraBinding
    private var bitmap: Bitmap? = null
    private val viewModel by viewModels<CameraViewModel> {
        CameraViewModelProvider(
            CameraRepoImpl(
                CameraDataSource()
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCameraBinding.bind(view)
        takeAPicture()

    }

    private fun uploadPost() {
        binding.btnUpload.setOnClickListener {
            bitmap?.let { bitmap ->
                viewModel.uploadPicture(bitmap, binding.postDescription.text.toString().trim())
                    .observe(viewLifecycleOwner,
                        Observer {
                            when(it){
                                is Resource.Loading -> {
                                    Toast.makeText(requireContext(),"Uploading photo...",Toast.LENGTH_LONG).show()
                                }
                                is Resource.Success -> {
                                    findNavController().navigate(R.id.action_cameraFragment_to_homeScreenFragment)
                                }
                                is Resource.Failure -> {
                                    Toast.makeText(requireContext(),"Error: ${it.exception}",Toast.LENGTH_LONG).show()

                                }
                            }
                        })
            }
        }
    }

    private fun takeAPicture() {
        binding.postImage.setOnClickListener {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            try {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(
                    requireContext(),
                    "No se ha encontrado aplicacion camara.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            binding.postImage.setImageBitmap(imageBitmap)
            bitmap = imageBitmap
        }
    }
}