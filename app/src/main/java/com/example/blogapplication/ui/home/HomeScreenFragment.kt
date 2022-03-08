package com.example.blogapplication.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.blogapplication.R
import com.example.blogapplication.databinding.FragmentHomeScreenBinding
import com.example.blogapplication.ui.home.adapter.HomeScreenAdapter


class HomeScreenFragment : Fragment(R.layout.fragment_home_screen) {

    private lateinit var binding: FragmentHomeScreenBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeScreenBinding.bind(view)



        binding.rvHome.adapter = HomeScreenAdapter()
    }

}