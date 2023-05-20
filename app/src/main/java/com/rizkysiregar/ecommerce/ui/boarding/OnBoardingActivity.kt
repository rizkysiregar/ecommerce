package com.rizkysiregar.ecommerce.ui.boarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.rizkysiregar.ecommerce.R
import com.rizkysiregar.ecommerce.databinding.ActivityOnBoardingBinding
import com.rizkysiregar.ecommerce.ui.login.LoginActivity

class OnBoardingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnBoardingBinding
    var currentItem = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewPager2 = binding.viewpager

        prepareViewPager()
        navigateToLoginActivity()

        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                if (position == 2){
                    binding.tvNext.visibility = View.GONE
                }else{
                    binding.tvNext.visibility = View.VISIBLE
                }
            }
        })
    }


    private fun prepareViewPager() {
        val viewPager = binding.viewpager
        viewPager.adapter = BoardingAdapter()
        val dotsIndicator = binding.springDotsIndicator
        dotsIndicator.attachTo(viewPager)
    }

    private fun navigateToLoginActivity() {
        binding.materialButton.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        binding.tvJump.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        binding.tvNext.setOnClickListener {
            binding.viewpager.currentItem += 1
            currentItem = binding.viewpager.currentItem
        }

    }
}