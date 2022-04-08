package com.example.blogapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.blogapplication.core.hide
import com.example.blogapplication.core.show
import com.example.blogapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)

        observeDestinationChange(navController)
    }

    private fun observeDestinationChange(navController: NavController) {
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {

                R.id.loginFragment -> {
                    binding.bottomNavigationView.hide()
                }

                R.id.registerFragment -> {
                    binding.bottomNavigationView.hide()
                }

                else -> {
                    binding.bottomNavigationView.show()
                }
            }
        }
    }
}