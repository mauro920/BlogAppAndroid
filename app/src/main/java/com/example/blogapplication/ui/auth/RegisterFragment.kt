package com.example.blogapplication.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.blogapplication.R
import com.example.blogapplication.data.remote.auth.AuthDataSource
import com.example.blogapplication.core.Resource
import com.example.blogapplication.databinding.FragmentRegisterBinding
import com.example.blogapplication.domain.auth.AuthRepoImpl
import com.example.blogapplication.presentation.auth.AuthViewModel
import com.example.blogapplication.presentation.auth.AuthViewModelProvider
import com.google.firebase.auth.FirebaseAuth

class RegisterFragment : Fragment(R.layout.fragment_register) {

    private lateinit var binding: FragmentRegisterBinding
    private val firebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val viewModel by viewModels<AuthViewModel> {
        AuthViewModelProvider(
            AuthRepoImpl(
                AuthDataSource()
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegisterBinding.bind(view)
        getSignUpData()
    }

    private fun getSignUpData() {
        binding.registerBtn.setOnClickListener {

            val username = binding.usernameTxt.text.toString().trim()
            val email = binding.emailTxt.text.toString().trim()
            val password = binding.passwordTxt.text.toString().trim()
            val confirmation = binding.confirmTxt.text.toString().trim()

            if (validateUserData(password, confirmation, username, email)) return@setOnClickListener

            createUser(email, password, username)
        }
    }

    private fun createUser(email: String, password: String, username: String) {
        viewModel.signUp(email, password, username).observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Loading -> {
                    binding.progressBarRegister.visibility = View.VISIBLE
                    binding.registerBtn.isEnabled = false
                }
                is Resource.Success -> {
                    binding.progressBarRegister.visibility = View.GONE
                    findNavController().navigate(R.id.action_registerFragment_to_homeScreenFragment)
                }
                is Resource.Failure -> {
                    binding.progressBarRegister.visibility = View.GONE
                    binding.registerBtn.isEnabled = true
                    Toast.makeText(requireContext(), "Error: ${it.exception}", Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun validateUserData(
        password: String,
        confirmation: String,
        username: String,
        email: String
    ): Boolean {
        if (password != confirmation) {
            binding.passwordInput.error = "Password doesn't match."
            binding.confirmInput.error = "Password doesn't match."
            binding.passwordInput.setEndIconActivated(false)
            binding.confirmInput.setEndIconActivated(false)
            return true
        }
        if (username.isEmpty()) {
            binding.usernameInput.error = "Username is Empty"
            return true
        }
        if (password.isEmpty()) {
            binding.passwordInput.error = "Password is Empty"
            return true
        }
        if (email.isEmpty()) {
            binding.emailInput.error = "Email is Empty"
            return true
        }
        if (confirmation.isEmpty()) {
            binding.confirmInput.error = "Confirmation is Empty"
            return true
        }
        return false
    }
}