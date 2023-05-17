package com.rizkysiregar.ecommerce.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.rizkysiregar.ecommerce.R
import com.rizkysiregar.ecommerce.databinding.ActivitySplashBinding
import com.rizkysiregar.ecommerce.ui.boarding.OnBoardingActivity

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // show splash and go to on boarding
        loadSplash()
    }

    private fun loadSplash() {
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, OnBoardingActivity::class.java))
            finish()
        }, TIME)
    }

    companion object {
        private const val TIME: Long = 1000
    }
}