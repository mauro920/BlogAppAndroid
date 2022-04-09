package com.example.blogapplication.ui.auth

import android.app.Activity
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.blogapplication.R
import com.example.blogapplication.core.Resource
import com.example.blogapplication.data.remote.auth.AuthDataSource
import com.example.blogapplication.databinding.FragmentSetupProfileBinding
import com.example.blogapplication.domain.auth.AuthRepoImpl
import com.example.blogapplication.presentation.auth.AuthViewModel
import com.example.blogapplication.presentation.auth.AuthViewModelProvider

class SetupProfileFragment : Fragment(R.layout.fragment_setup_profile) {

    private var bitmap: Bitmap? = null
    private val REQUEST_IMAGE_CAPTURE = 12
    private lateinit var binding: FragmentSetupProfileBinding
    private val viewModel by viewModels<AuthViewModel> {
        AuthViewModelProvider(
            AuthRepoImpl(
                AuthDataSource()
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSetupProfileBinding.bind(view)
        binding.profilePicture.setOnClickListener {
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

        binding.btnSave.setOnClickListener {
            val username = binding.usernameTxt.text.toString().trim()
            val alertDialog = AlertDialog.Builder(requireContext()).setTitle("Uploading photo...").create()
            bitmap?.let {
                if (username.isNotEmpty()) {
                    viewModel.updateUserProfile(it, username).observe(viewLifecycleOwner, Observer { result ->
                        when (result) {
                            is Resource.Loading -> {
                                alertDialog.show()
                            }
                            is Resource.Success -> {
                                alertDialog.dismiss()
                                findNavController().navigate(R.id.action_setupProfileFragment_to_homeScreenFragment)
                            }
                            is Resource.Failure -> {
                                alertDialog.dismiss()
                            }
                        }
                    })
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            binding.profilePicture.setImageBitmap(imageBitmap)
            bitmap = imageBitmap
        }
    }
}