package com.example.blogapplication.ui.home

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.blogapplication.R
import com.example.blogapplication.core.Resource
import com.example.blogapplication.core.hide
import com.example.blogapplication.core.show
import com.example.blogapplication.data.remote.home.HomeScreenDataSource
import com.example.blogapplication.databinding.FragmentHomeScreenBinding
import com.example.blogapplication.domain.home.HomeScreenRepoImpl
import com.example.blogapplication.presentation.HomeScreenViewModel
import com.example.blogapplication.presentation.HomeScreenViewModelFactory
import com.example.blogapplication.ui.home.adapter.HomeScreenAdapter


class HomeScreenFragment : Fragment(R.layout.fragment_home_screen) {

    private lateinit var binding: FragmentHomeScreenBinding
    private val viewModel by viewModels<HomeScreenViewModel> {
        HomeScreenViewModelFactory(
            HomeScreenRepoImpl(
                HomeScreenDataSource()
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeScreenBinding.bind(view)

        viewModel.fetchLatestPosts().observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Loading -> {
                    binding.progressBar.show()
                }
                is Resource.Success -> {
                    binding.progressBar.hide()
                    if(it.data.isEmpty()){
                        binding.emptyContainer.show()
                        return@Observer
                    } else {
                        binding.emptyContainer.hide()
                    }
                    binding.rvHome.adapter = HomeScreenAdapter(it.data)
                }
                is Resource.Failure -> {
                    binding.progressBar.hide()
                    Toast.makeText(
                        requireContext(),
                        "Ocurrio un error: ${it.exception}",
                        Toast.LENGTH_LONG
                    ).show()
                    Log.d("mauropena","${it.exception}")
                }
            }

        })
    }

}