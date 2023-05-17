package com.rizkysiregar.ecommerce.ui.boarding

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.rizkysiregar.ecommerce.R
import com.rizkysiregar.ecommerce.databinding.FragmentFirstOnBoardingBinding
import com.rizkysiregar.ecommerce.ui.login.LoginActivity
import com.rizkysiregar.ecommerce.ui.register.RegisterActivity

class FirstOnBoardingFragment : Fragment() {

    private var _binding: FragmentFirstOnBoardingBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnJoin = binding.btnDone
        btnJoin.setOnClickListener {
            startActivity(Intent(requireActivity(), LoginActivity::class.java))
        }

        binding.tvLewati.setOnClickListener {
            startActivity(Intent(requireActivity(), LoginActivity::class.java))
            requireActivity().finish()
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstOnBoardingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}