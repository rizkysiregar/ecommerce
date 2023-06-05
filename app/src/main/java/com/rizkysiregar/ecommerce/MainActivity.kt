package com.rizkysiregar.ecommerce

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.rizkysiregar.ecommerce.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupNavigation()
    }

    private fun setupNavigation() {
        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_container)
        navView.setupWithNavController(navController)

        // set appbar and bottom nav visibility in particular fragment
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.navigation_search -> {
                    navView.visibility = View.GONE
                    binding.appBarLayout.visibility = View.GONE
                }

                R.id.navigation_detail -> {
                    binding.materialToolbar.setNavigationOnClickListener {
                        findNavController(R.id.nav_host_fragment_container).navigateUp()
                    }
                    navView.visibility = View.GONE
                    binding.materialToolbar.title = "Detail Product"
                    binding.materialToolbar.navigationIcon =
                        ContextCompat.getDrawable(this, R.drawable.baseline_arrow_back_24)
                }

                else -> {
                    navView.visibility = View.VISIBLE
                    binding.appBarLayout.visibility = View.VISIBLE
                    binding.materialToolbar.title = "RizkySiregar"
                    binding.materialToolbar.navigationIcon =
                        ContextCompat.getDrawable(this, R.drawable.account_circle_fill)
                }
            }
        }
    }

    override fun onBackPressed() {
        val navController = findNavController(R.id.nav_host_fragment_container)
        if (!navController.navigateUp()) {
            super.onBackPressed()
        }
    }

}