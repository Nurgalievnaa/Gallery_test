package com.example.gallery_test.view.authorization

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.gallery_test.App
import com.example.gallery_test.BaseFragment
import com.example.gallery_test.R
import com.example.gallery_test.databinding.FragmentRegistrationBinding
import com.example.gallery_test.factory.RegistrationViewModelFactory
import com.example.gallery_test.model.TokenResponse
import com.example.gallery_test.utils.toast
import com.example.gallery_test.view.BottomNavigationFragment
import com.example.gallery_test.view.welcomepage.WelcomePageFragment
import javax.inject.Inject


class RegistrationFragment : BaseFragment() {
    private lateinit var binding: FragmentRegistrationBinding

    @Inject
    lateinit var registerViewModelFactory: RegistrationViewModelFactory

    private lateinit var registrationViewModel: RegistrationViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as App).applicationComponent.inject(this)
        registrationViewModel = ViewModelProvider(this, registerViewModelFactory).get(RegistrationViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registrationViewModel.isBirthdayValid.observe(viewLifecycleOwner) { isValid ->
            if (isValid) {
                binding.birthdayInputLayout.error = null
            } else {
                binding.birthdayInputLayout.error = "Invalid date format or values"
            }
        }

        binding.signUpButton.setOnClickListener {
            registrationViewModel.register(
                binding.usernameEditText.text.toString(),
                binding.emailEditText.text.toString(),
                binding.birthdayEditText.text.toString(),
                binding.passwordEditText.text.toString(),
                binding.confirmPasswordEditText.text.toString()
            )
        }

        binding.signInButton.setOnClickListener {
            replaceFragment(R.id.fragment_container, LoginFragment())
        }

        binding.usernameEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
            override fun afterTextChanged(s: Editable?) {
                val username = s.toString()
                binding.usernameInputLayout.error = if (username.length < 6) {
                    "Username must be at least 6 characters long"
                } else {
                    ""
                }
            }
        })

        binding.emailEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
            override fun afterTextChanged(s: Editable?) {
                val email = s.toString()
                binding.emailInputLayout.error = if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    "Invalid email"
                } else {
                    ""
                }
            }
        })

        binding.birthdayEditText.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val date = binding.birthdayEditText.text?.toString()?.trim()
                registrationViewModel.validateBirthday(date)
            }
        }

        binding.birthdayInputLayout.hint = "yyyy-mm-dd"

        binding.passwordEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
            override fun afterTextChanged(s: Editable?) {
                val password = s.toString()
                binding.passwordInputLayout.error = if (password.length < 6) {
                    "Password must be at least 6 characters long"
                } else {
                    ""
                }
            }
        })

        binding.confirmPasswordEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
            override fun afterTextChanged(s: Editable?) {
                val confirmPassword = s.toString()
                binding.confirmPasswordInputLayout.error =
                    if (binding.passwordEditText.text.toString() != confirmPassword) {
                        "Passwords do not match"
                    } else {
                        ""
                    }
            }
        })

        binding.cancelButtonOfRegister.setOnClickListener {
            replaceFragment(R.id.fragment_container, WelcomePageFragment())
        }
        setupObservers()
    }

    private fun setupObservers() {
        registrationViewModel.registerLiveData.observe(viewLifecycleOwner) { response ->
            when (response) {
                is RegisterResponse.Error -> showError(response)
                is RegisterResponse.Success -> registerSuccess(response.tokenResponse)
            }
        }
    }

    private fun showError(response: RegisterResponse.Error) {
        context?.toast(response.error.localizedMessage.orEmpty())
    }

    private fun registerSuccess(tokenResponse: TokenResponse) {
        val sharedPreferences = requireContext().getSharedPreferences("user_data", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putString("user_token", tokenResponse.accessToken)
            apply()
        }

        replaceFragment(R.id.fragment_container, BottomNavigationFragment())
        context?.toast("Registration successful")
    }
}
