package com.rizkysiregar.ecommerce.ui.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.Html
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.rizkysiregar.ecommerce.MainActivity
import com.rizkysiregar.ecommerce.R
import com.rizkysiregar.ecommerce.data.model.RegisterModel
import com.rizkysiregar.ecommerce.databinding.ActivityLoginBinding
import com.rizkysiregar.ecommerce.ui.register.RegisterActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkInput()
        coloredText()

        // event to mainActivity
        val btnLogin = binding.btnMasukLogin
        btnLogin.setOnClickListener {
            val email = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()

            val modelData = RegisterModel(email, password)
            try {
                loginViewModel.loginUser(modelData)
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } catch (e: Exception) {
                Toast.makeText(this, "Error: ${e.message.toString()}", Toast.LENGTH_SHORT).show()
            }

        }

        binding.btnDaftarLogin.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
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
                if (s.toString().isEmpty()) {
                    binding.textInputLayoutEmail.boxStrokeColor =
                        ContextCompat.getColor(this@LoginActivity, R.color.indicator_filled)
                    binding.tvEmailError.visibility = View.GONE
                    binding.btnMasukLogin.isEnabled = false
                } else if (isValidEmail) {
                    binding.textInputLayoutEmail.boxStrokeColor =
                        ContextCompat.getColor(this@LoginActivity, R.color.indicator_filled)
                    binding.tvEmailError.visibility = View.GONE
                    binding.edtEmail.error = null
                    binding.btnMasukLogin.isEnabled = true
                } else {
                    binding.textInputLayoutEmail.boxStrokeColor =
                        ContextCompat.getColor(this@LoginActivity, R.color.text_error)
                    binding.tvEmailError.visibility = View.VISIBLE
                    binding.btnMasukLogin.isEnabled = false
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
                        ContextCompat.getColor(this@LoginActivity, R.color.indicator_filled)
                    binding.btnMasukLogin.isEnabled = false
                } else if (s.toString().length < 8) {
                    binding.textInputLayoutPassword.boxStrokeColor =
                        ContextCompat.getColor(this@LoginActivity, R.color.text_error)
                    binding.tvPasswordMessage.visibility = View.VISIBLE
                    binding.btnMasukLogin.isEnabled = false
                } else {
                    binding.textInputLayoutPassword.boxStrokeColor =
                        ContextCompat.getColor(this@LoginActivity, R.color.indicator_filled)
                    binding.tvPasswordMessage.visibility = View.GONE
                    binding.btnMasukLogin.isEnabled = true
                }
            }

            override fun afterTextChanged(p0: Editable?) {
                //
            }

        })

    }

    private fun coloredText() {
        val coloredText = getString(R.string.colored_text)
        binding.tvTermsLogin.text = Html.fromHtml(coloredText, Html.FROM_HTML_MODE_LEGACY)
    }
}