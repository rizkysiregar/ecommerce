package com.rizkysiregar.ecommerce.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.rizkysiregar.ecommerce.data.local.db.EcommerceDao
import com.rizkysiregar.ecommerce.data.local.preference.PreferenceManager
import com.rizkysiregar.ecommerce.databinding.FragmentHomeBinding
import com.rizkysiregar.ecommerce.ui.login.LoginActivity
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.koin.android.ext.android.inject

@OptIn(DelicateCoroutinesApi::class)
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val ecommerceDao: EcommerceDao by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogout.setOnClickListener {
            runBlocking {
                PreferenceManager.setRefreshToken(requireContext(), "")
                PreferenceManager.setAccessToken(requireContext(), "")
                PreferenceManager.setIsLogin(requireContext(), false)
                startActivity(Intent(requireContext(), LoginActivity::class.java))
                requireActivity().finish()
            }

            // clear room table
            GlobalScope.launch(Dispatchers.IO) {
                ecommerceDao.clearTblCart()
                ecommerceDao.clearTblNotification()
                ecommerceDao.clearTblWishlist()
            }
        }

        binding.btnCrash.setOnClickListener {
            throw RuntimeException("Test Crash")
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }
}