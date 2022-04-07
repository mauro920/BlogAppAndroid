package com.example.blogapplication.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.blogapplication.R
import com.example.blogapplication.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var binding: FragmentLoginBinding
    private val firebaseAuth by lazy { FirebaseAuth.getInstance() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)
        isUserLogged()
        doLogin()
    }


    private fun isUserLogged() {
        firebaseAuth.currentUser?.let {
            findNavController().navigate(R.id.action_loginFragment_to_homeScreenFragment)
        }
    }

    private fun doLogin(){
        binding.loginBtn.setOnClickListener {
            val email = binding.emailInput.editText.toString().trim()
            val password = binding.passwordInput.editText.toString().trim()
            validateCredentials(email,password)
            login(email,password)
        }
    }

    private fun validateCredentials(email: String, password: String){
        if (email.isEmpty()){
            binding.emailInput.error = "E-Mail is empty"
        }
        if (password.isEmpty()){
            binding.passwordInput.error = "Password is empty"
        }
    }
    private fun login(email:String, password: String){

    }
}