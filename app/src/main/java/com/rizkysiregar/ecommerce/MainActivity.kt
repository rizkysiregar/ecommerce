package com.rizkysiregar.ecommerce

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.rizkysiregar.ecommerce.databinding.ActivityMainBinding
import com.rizkysiregar.ecommerce.databinding.ActivitySplashBinding
import com.rizkysiregar.ecommerce.ui.boarding.OnBoardingActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

}