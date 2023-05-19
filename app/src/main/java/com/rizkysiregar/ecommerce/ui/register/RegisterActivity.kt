package com.rizkysiregar.ecommerce.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.rizkysiregar.ecommerce.R
import com.rizkysiregar.ecommerce.databinding.ActivityRegisterBinding
import com.rizkysiregar.ecommerce.ui.login.LoginActivity
import com.rizkysiregar.ecommerce.ui.profile.ProfileActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.Exception

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    private val registerViewModel: RegisterViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // check input edit text and password
        checkInput()

        // hit register api
        try {
            register()
        } catch (e: Exception) {
            Toast.makeText(this, e.message.toString(), Toast.LENGTH_SHORT).show()
        }

        // show loading
        registerViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        // button event
        binding.btnDaftarRegister.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
            finish()
        }

        binding.btnMasukRegister.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

    }

    private fun checkInput() {
        // variable declaration
        val edtEmail = binding.edtEmail
        val edtPassword = binding.edtPassword

        // listener for change in edtEmail
        edtEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                if (s.isNullOrEmpty()) {
                    binding.edtEmail.error = null
                }
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val isValidEmail: Boolean = Patterns.EMAIL_ADDRESS.matcher(s).matches()
                if (s.toString().isEmpty()){
                    binding.textInputLayoutEmail.boxStrokeColor =
                        ContextCompat.getColor(this@RegisterActivity, R.color.indicator_filled)
                    binding.tvEmailError.visibility = View.GONE
                    binding.btnDaftarRegister.isEnabled = false
                }
                else if (isValidEmail) {
                    binding.textInputLayoutEmail.boxStrokeColor =
                        ContextCompat.getColor(this@RegisterActivity, R.color.indicator_filled)
                    binding.tvEmailError.visibility = View.GONE
                    binding.edtEmail.error = null
                    binding.btnDaftarRegister.isEnabled = true
                } else {
                    binding.textInputLayoutEmail.boxStrokeColor =
                        ContextCompat.getColor(this@RegisterActivity, R.color.text_error)
                    binding.tvEmailError.visibility = View.VISIBLE
                    binding.btnDaftarRegister.isEnabled = false
                }
            }

            override fun afterTextChanged(p0: Editable?) {
                //
            }
        })

        edtPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (s.isNullOrEmpty()) {
                    binding.edtPassword.error = null
                }
            }
            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (s.toString().isEmpty()) {
                    binding.tvPasswordMessage.visibility = View.GONE
                    binding.textInputLayoutPassword.boxStrokeColor =
                        ContextCompat.getColor(this@RegisterActivity, R.color.indicator_filled)
                    binding.btnDaftarRegister.isEnabled = false
                } else if (s.toString().length < 8) {
                    binding.textInputLayoutPassword.boxStrokeColor =
                        ContextCompat.getColor(this@RegisterActivity, R.color.text_error)
                    binding.tvPasswordMessage.visibility = View.VISIBLE
                    binding.btnDaftarRegister.isEnabled = false
                } else {
                    binding.textInputLayoutPassword.boxStrokeColor =
                        ContextCompat.getColor(this@RegisterActivity, R.color.indicator_filled)
                    binding.tvPasswordMessage.visibility = View.GONE
                    binding.btnDaftarRegister.isEnabled = true
                }
            }

            override fun afterTextChanged(p0: Editable?) {
                //
            }

        })

    }

    private fun register() {
        val email = binding.edtEmail.toString()
        val password = binding.edtPassword.toString()
        registerViewModel.postRegister(email, password)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.pbRegister.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}