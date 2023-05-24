package com.rizkysiregar.ecommerce.ui.register

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
import com.rizkysiregar.ecommerce.R
import com.rizkysiregar.ecommerce.data.local.preference.PreferenceManager
import com.rizkysiregar.ecommerce.data.model.RegisterModel
import com.rizkysiregar.ecommerce.databinding.ActivityRegisterBinding
import com.rizkysiregar.ecommerce.ui.login.LoginActivity
import com.rizkysiregar.ecommerce.ui.profile.ProfileActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val registerViewModel: RegisterViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkInput()
        coloredText()

        binding.btnDaftarRegister.setOnClickListener {
            try {
                register()
                setPreference()
                val isAccessTokenNull = PreferenceManager.getAccessToken(this).toString()
                if (isAccessTokenNull.isEmpty()) {
                    Toast.makeText(this, "Token is not set in preference", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    startActivity(Intent(this, ProfileActivity::class.java))
                    finish()
                }
            } catch (e: Exception) {
                Toast.makeText(this, "Error: ${e}", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnMasukRegister.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun register() {
        val edtEmail = binding.edtEmail.text.toString()
        val edtPassword = binding.edtPassword.text.toString()
        val data = RegisterModel(edtEmail, edtPassword)
        registerViewModel.registerNewUser(data)
    }

    private fun setPreference() {
        registerViewModel.data.observe(this) {
            PreferenceManager.setAccessToken(this, it.data.accessToken)
            PreferenceManager.setRefreshToken(this, it.data.refreshToken)
        }
    }

    private fun checkInput() {
        // variable declaration
        val edtEmail = binding.edtEmail
        val edtPassword = binding.edtPassword

        // listener for change in edtEmail
        edtEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
                if (s.isNullOrEmpty()) {
                    binding.edtEmail.error = null
                }
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val isValidEmail: Boolean = Patterns.EMAIL_ADDRESS.matcher(s).matches()
                if (s.toString().isEmpty()) {
                    binding.textInputLayoutEmail.boxStrokeColor =
                        ContextCompat.getColor(this@RegisterActivity, R.color.indicator_filled)
                    binding.tvEmailError.visibility = View.GONE
                    binding.btnDaftarRegister.isEnabled = false
                } else if (isValidEmail) {
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

    private fun coloredText() {
        val coloredText = getString(R.string.colored_text)
        binding.tvTerms.text = Html.fromHtml(coloredText, Html.FROM_HTML_MODE_LEGACY)
    }

}