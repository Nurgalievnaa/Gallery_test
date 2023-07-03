package com.example.gallery_test.view

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.gallery_test.R
import com.example.gallery_test.api.ApiServiceBuilder
import com.example.gallery_test.databinding.FragmentRegistrationBinding
import com.example.gallery_test.viewModel.RegistrationViewModel
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class RegistrationFragment : Fragment() {
    private lateinit var binding: FragmentRegistrationBinding
    private lateinit var registrationViewModel: RegistrationViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPreferences =
            requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val apiService = ApiServiceBuilder.createApiService()
        registrationViewModel =
            ViewModelProvider(this, RegistrationViewModelFactory(sharedPreferences, apiService))
                .get(RegistrationViewModel::class.java)

        binding.signUpButton.setOnClickListener {
            val username = binding.usernameEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val birthday = binding.birthdayEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            val confirmPassword = binding.confirmPasswordEditText.text.toString()

            //todo add to disposable
            //todo move subscribe to viewModel
            registrationViewModel.register(username, email, birthday, password, confirmPassword)
                .subscribe({
                    activity?.runOnUiThread {
                        parentFragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, BottomNavigationFragment())
                            .commit()
                        Toast.makeText(
                            requireContext(), "You are registered",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }, {
                    activity?.runOnUiThread {
                        Toast.makeText(
                            requireContext(), "Registration failed. Please try again.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
        }

        binding.signInButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, LoginFragment()).commit()
        }


        binding.usernameEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                val username = s.toString()
                //todo simplify
                if (username.length < 6) {
                    binding.usernameInputLayout.error = "Username must be at least 6 characters long"
                } else {
                    binding.usernameInputLayout.error = ""
                }
            }
        })

        binding.emailEditText.addTextChangedListener(object : TextWatcher {
            //todo = Unit
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                val email = s.toString()
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    binding.emailInputLayout.error = "Invalid email"
                } else {
                    binding.emailInputLayout.error = ""
                }
            }
        })

        binding.birthdayEditText.addTextChangedListener(object : TextWatcher {

            //todo remove simple date format
            private var isFormatting = false
            private val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
            private val minimumAllowedDate: Calendar = Calendar.getInstance().apply {
                set(Calendar.YEAR, 2006)
            }

            init {
                dateFormat.isLenient = false
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (isFormatting) return
                isFormatting = true

                val unformattedText = s?.toString()?.replace("[^\\d-]".toRegex(), "")
                val formattedText = try {
                    val parsedDate = unformattedText?.let { dateFormat.parse(it) }
                    if (parsedDate != null && parsedDate <= minimumAllowedDate.time) {
                        parsedDate?.let { dateFormat.format(it) }
                    } else {
                        ""
                    }
                } catch (e: ParseException) {
                    unformattedText
                }

                formattedText?.let {
                    s?.replace(0, s.length, it)
                    binding.birthdayEditText.setSelection(it.length)
                    if (formattedText.isEmpty()) {
                        binding.birthdayInputLayout.error = "Minimum age required is 18 years or Date is invalid"
                    } else {
                        binding.birthdayInputLayout.error = null
                    }
                }

                isFormatting = false
            }
        })

        binding.birthdayInputLayout.hint = "dd-MM-yyyy"

        binding.passwordEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                val password = s.toString()
                //todo simplify
                if (password.length < 6) {
                    binding.passwordInputLayout.error = "Password must be at least 6 characters long"
                } else {
                    binding.passwordInputLayout.error = ""
                }
            }
        })

        binding.confirmPasswordEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                val password = binding.passwordEditText.text.toString()
                val confirmPassword = s.toString()
                //todo simplify
                if (password != confirmPassword) {
                    binding.confirmPasswordInputLayout.error = "Passwords do not match"
                } else {
                    binding.confirmPasswordInputLayout.error = ""
                }
            }
        })

        binding.cancelButtonOfRegister.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, WelcomePageFragment())
                .commit()
        }
    }
}
