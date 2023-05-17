package com.rizkysiregar.ecommerce

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.rizkysiregar.ecommerce.databinding.ActivityMainBinding
import com.rizkysiregar.ecommerce.databinding.ActivitySplashBinding
import com.rizkysiregar.ecommerce.ui.boarding.OnBoardingActivity

class MainActivity : AppCompatActivity(){

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_container)

//        val appConfiguration = AppBarConfiguration.Builder(
//            R.id.navigation_home, R.id.navigation_store, R.id.navigation_wishlist, R.id.navigation_transaction
//        ).build()

        navView.setupWithNavController(navController)

    }

}