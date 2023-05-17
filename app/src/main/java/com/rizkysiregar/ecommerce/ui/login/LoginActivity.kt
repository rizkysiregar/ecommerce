package com.rizkysiregar.ecommerce.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rizkysiregar.ecommerce.MainActivity
import com.rizkysiregar.ecommerce.R
import com.rizkysiregar.ecommerce.databinding.ActivityLoginBinding
import com.rizkysiregar.ecommerce.ui.profile.ProfileActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // event to mainActivity
        val btnLogin = binding.btnMasuk
        btnLogin.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
    }
}