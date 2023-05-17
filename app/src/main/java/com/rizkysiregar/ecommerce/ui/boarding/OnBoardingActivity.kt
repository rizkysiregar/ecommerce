package com.rizkysiregar.ecommerce.ui.boarding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.rizkysiregar.ecommerce.R
import com.rizkysiregar.ecommerce.databinding.ActivityOnBoardingBinding

class OnBoardingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnBoardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sectionPagerAdapter = SectionPagerAdapter(this)
        val viewPager: ViewPager2 = binding.viewpager
        val dotsIndicator = binding.springDotsIndicator

        viewPager.adapter = sectionPagerAdapter
        dotsIndicator.attachTo(viewPager)
    }
}