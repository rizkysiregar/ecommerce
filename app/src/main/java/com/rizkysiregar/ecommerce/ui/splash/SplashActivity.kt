package com.rizkysiregar.ecommerce.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.rizkysiregar.ecommerce.data.local.preference.PreferenceManager
import com.rizkysiregar.ecommerce.databinding.ActivitySplashBinding
import com.rizkysiregar.ecommerce.ui.boarding.OnBoardingActivity
import com.rizkysiregar.ecommerce.ui.login.LoginActivity


@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // show splash and go to on boarding or login
        loadSplash()
    }

    private fun loadSplash() {
        Handler(Looper.getMainLooper()).postDelayed({
            val isOnboardingCompleted = PreferenceManager.isOnboardingCompleted(this)
            if (isOnboardingCompleted) {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            } else {
                startActivity(Intent(this, OnBoardingActivity::class.java))
                finish()
            }
        }, TIME)
    }

    companion object {
        private const val TIME: Long = 1000
    }
}