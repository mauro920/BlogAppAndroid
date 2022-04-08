package com.example.blogapplication.ui.auth

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.blogapplication.R
import com.example.blogapplication.core.Resource
import com.example.blogapplication.data.remote.auth.AuthDataSource
import com.example.blogapplication.databinding.FragmentLoginBinding
import com.example.blogapplication.domain.auth.AuthRepoImpl
import com.example.blogapplication.presentation.auth.AuthViewModel
import com.example.blogapplication.presentation.auth.AuthViewModelProvider
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var binding: FragmentLoginBinding
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
        binding = FragmentLoginBinding.bind(view)
        isUserLogged()
        doLogin()
        goToRegisterPage()
    }


    private fun isUserLogged() {
        firebaseAuth.currentUser?.let {
            findNavController().navigate(R.id.action_loginFragment_to_homeScreenFragment)
        }
    }

    private fun doLogin() {
        binding.loginBtn.setOnClickListener {
            val email = binding.emailTxt.text.toString().trim()
            val password = binding.passwordTxt.text.toString().trim()
            validateCredentials(email, password)
            login(email, password)
        }
    }
    private fun goToRegisterPage(){
        binding.registerBtn.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    private fun validateCredentials(email: String, password: String) {
        if (email.isEmpty()) {
            binding.emailInput.error = "E-Mail is empty"
        }
        if (password.isEmpty()) {
            binding.passwordInput.error = "Password is empty"
        }
    }

    private fun login(email: String, password: String) {
        viewModel.signIn(email, password).observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Loading -> {
                    binding.progressBarLogin.visibility = View.VISIBLE
                    binding.loginBtn.isEnabled = false
                }
                is Resource.Success -> {
                    binding.progressBarLogin.visibility = View.GONE
                    findNavController().navigate(R.id.action_loginFragment_to_homeScreenFragment)
                }
                is Resource.Failure -> {
                    binding.progressBarLogin.visibility = View.GONE
                    binding.loginBtn.isEnabled = true
                    Toast.makeText(
                        requireContext(),
                        "Error: ${it.exception}",
                        Toast.LENGTH_LONG)
                        .show()
                    Log.d("fire","${it.exception}")
                }
            }
        })
    }
}