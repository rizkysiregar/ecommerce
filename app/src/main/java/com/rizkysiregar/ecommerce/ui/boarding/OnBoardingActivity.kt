package com.rizkysiregar.ecommerce.ui.boarding

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.rizkysiregar.ecommerce.R
import com.rizkysiregar.ecommerce.data.local.preference.PreferenceManager
import com.rizkysiregar.ecommerce.data.model.DataBoarding
import com.rizkysiregar.ecommerce.databinding.ActivityOnBoardingBinding
import com.rizkysiregar.ecommerce.ui.login.LoginActivity
import com.rizkysiregar.ecommerce.ui.register.RegisterActivity

class OnBoardingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnBoardingBinding
    var currentItem = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewPager2 = binding.viewpager

        prepareViewPager()
        navigateToRegisterActivity()

        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                if (position == 2) {
                    binding.tvNext.visibility = View.GONE
                } else {
                    binding.tvNext.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun boardingData(): List<DataBoarding> {
        return listOf(
            DataBoarding(R.drawable.asset1),
            DataBoarding(R.drawable.asset2),
            DataBoarding(R.drawable.asset3),
        )
    }

    private fun prepareViewPager() {
        val data = boardingData()
        val viewPager = binding.viewpager
        viewPager.adapter = BoardingAdapter(data = data)
        val dotsIndicator = binding.springDotsIndicator
        dotsIndicator.attachTo(viewPager)
    }

    private fun navigateToRegisterActivity() {
        binding.materialButton.setOnClickListener {
            checkPreference()
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }

        binding.tvJump.setOnClickListener {
            checkPreference()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        binding.tvNext.setOnClickListener {
            binding.viewpager.currentItem += 1
            currentItem = binding.viewpager.currentItem
        }
    }

    private fun checkPreference() {
        try {
            // setOnboarding completed
            PreferenceManager.setOnboardoingPreference(this, true)
        } catch (e: Exception) {
            Toast.makeText(this, "Error: ${e.message.toString()}", Toast.LENGTH_SHORT).show()
        }
    }


}
