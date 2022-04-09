package com.example.blogapplication.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.blogapplication.R
import com.example.blogapplication.data.remote.auth.AuthDataSource
import com.example.blogapplication.databinding.FragmentSetupProfileBinding
import com.example.blogapplication.domain.auth.AuthRepoImpl
import com.example.blogapplication.presentation.auth.AuthViewModel
import com.example.blogapplication.presentation.auth.AuthViewModelProvider

class SetupProfileFragment : Fragment(R.layout.fragment_setup_profile) {

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
    }
}