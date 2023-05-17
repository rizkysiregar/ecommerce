package com.rizkysiregar.ecommerce.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import com.rizkysiregar.ecommerce.databinding.ActivityRegisterBinding
import com.rizkysiregar.ecommerce.ui.login.LoginActivity
import com.rizkysiregar.ecommerce.ui.profile.ProfileActivity

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnMasuk.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        binding.btnRegister.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
            finish()
        }



        // check input edit text and password
        checkInput()
    }

    private fun checkInput() {
        // variable declaration
        val edtEmail = binding.edtEmail
        val edtPassword = binding.edtPassword

        // listener for change in edtEmail
        edtEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                edtEmail.error = null
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val isValidEmail: Boolean = Patterns.EMAIL_ADDRESS.matcher(s).matches()
                if (isValidEmail) {
                    binding.tvEmailError.visibility = View.GONE
                    binding.edtEmail.error = null
                    binding.btnRegister.isEnabled = true
                } else {
                    binding.tvEmailError.visibility = View.VISIBLE
                    binding.btnRegister.isEnabled = false
                }
            }

            override fun afterTextChanged(p0: Editable?) {
                //
            }
        })

        edtPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                edtPassword.error = null
            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (s.toString().length < 8) {
                    binding.tvPasswordMessage.visibility = View.VISIBLE
                    binding.btnRegister.isEnabled = false
                } else {
                    binding.tvPasswordMessage.visibility = View.GONE
                    binding.btnRegister.isEnabled = true
                }
            }

            override fun afterTextChanged(p0: Editable?) {
                //
            }

        })

    }

}