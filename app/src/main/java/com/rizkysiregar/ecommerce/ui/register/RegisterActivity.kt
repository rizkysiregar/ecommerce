package com.rizkysiregar.ecommerce.ui.register

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.text.HtmlCompat
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import com.rizkysiregar.ecommerce.R
import com.rizkysiregar.ecommerce.data.local.preference.PreferenceManager
import com.rizkysiregar.ecommerce.data.model.RegisterLoginModel
import com.rizkysiregar.ecommerce.databinding.ActivityRegisterBinding
import com.rizkysiregar.ecommerce.ui.login.LoginActivity
import com.rizkysiregar.ecommerce.ui.profile.ProfileActivity
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val registerViewModel: RegisterViewModel by viewModel()
    private val firebaseAnalytics: FirebaseAnalytics by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        coloredText()
        checkInput()

        binding.btnDaftarRegister.setOnClickListener {
            try {
                register()
                setPreference()
                registerViewModel.data.observe(this) {
                    val isRegisterSuccess = it.message
                    if (isRegisterSuccess == "OK") {
                        Toast.makeText(this, "Welcome", Toast.LENGTH_SHORT)
                            .show()
                        PreferenceManager.setIsLogin(this, true)
                        startActivity(Intent(this, ProfileActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this, it.message, Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            } catch (e: Exception) {
                Toast.makeText(this, "Error: $e", Toast.LENGTH_SHORT).show()
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

        // firebase token
        Firebase.messaging.token.addOnCompleteListener(
            OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w(TAG, "Fetching FCM registration FAILED", task.exception)
                    return@OnCompleteListener
                }

                // Get new registration token
                val token = task.result
                val data = RegisterLoginModel(edtEmail, edtPassword, token)
                registerViewModel.registerNewUser(data)
            }
        )

        val bundleEmail = bundleOf().apply {
            putString(FirebaseAnalytics.Param.ITEM_ID, "Email")
        }
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SIGN_UP, bundleEmail)
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
        val coloredText = getString(R.string.terms_and_conditions_login)
        binding.tvTerms.text = HtmlCompat.fromHtml(coloredText,
            HtmlCompat.FROM_HTML_MODE_LEGACY
        )
    }

    companion object {
        private const val TAG = "RegisterActivity"
    }

}