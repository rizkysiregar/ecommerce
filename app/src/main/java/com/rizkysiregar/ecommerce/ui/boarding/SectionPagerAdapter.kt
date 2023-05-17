package com.rizkysiregar.ecommerce.ui.boarding

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FirstOnBoardingFragment()
            1 -> fragment = SecondOnBoardingFragment()
            2 -> fragment = ThirdOnBoardingFragment()
        }
        return fragment as Fragment
    }

}