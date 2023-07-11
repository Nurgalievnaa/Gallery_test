package com.example.gallery_test.view.authorization

import android.annotation.SuppressLint
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
import com.example.gallery_test.utils.toast
import com.example.gallery_test.view.BottomNavigationFragment
import com.example.gallery_test.view.welcomepage.WelcomePageFragment
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject


class RegistrationFragment : BaseFragment() {
    private lateinit var binding: FragmentRegistrationBinding

    @Inject
    lateinit var registerViewModelFactory: RegistrationViewModelFactory

    private lateinit var registrationViewModel: RegistrationViewModel
    private val compositeDisposable = CompositeDisposable()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as App).applicationComponent.inject(this)
        registrationViewModel = ViewModelProvider(this, registerViewModelFactory)[RegistrationViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.signUpButton.setOnClickListener {
            val username = binding.usernameEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val birthday = binding.birthdayEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            val confirmPassword = binding.confirmPasswordEditText.text.toString()

            //todo move subscribe to viewModel
            val disposable = registrationViewModel.register(username, email, birthday, password, confirmPassword)
                .subscribe({
                    activity?.runOnUiThread {
                        replaceFragment(R.id.fragment_container, BottomNavigationFragment())
                        context?.toast("You are registered")
                    }
                }, {
                    activity?.runOnUiThread {
                        context?.toast("Registration failed. Please try again.")
                    }
                })
            compositeDisposable.add(disposable)
        }

        binding.signInButton.setOnClickListener {
            replaceFragment(R.id.fragment_container, LoginFragment())
        }

        binding.usernameEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

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
                if (date.isNullOrEmpty()) {
                    binding.birthdayInputLayout.error = "Date is required"
                } else if (!date.matches(Regex("\\d{4}-\\d{2}-\\d{2}"))) {
                    // Date format is invalid, show error message
                    binding.birthdayInputLayout.error = "Invalid date format. Use yyyy-mm-dd"
                } else {
                    val parts = date.split("-")
                    val year = parts[0].toInt()
                    val month = parts[1].toInt()
                    val day = parts[2].toInt()

                    if (year < 1920 || year > 2006) {
                        binding.birthdayInputLayout.error = "Invalid year. Must be between 1900 and 2100"
                    } else if (month == 2 && day > 28) {
                        binding.birthdayInputLayout.error = "Invalid. February is until 28"
                    } else if ((month == 4 || month == 6 || month == 9 || month == 11) && day > 30) {
                        binding.birthdayInputLayout.error = "Invalid month. Month is finished on 30"
                    } else if (month < 1 || month > 12) {
                        binding.birthdayInputLayout.error = "Invalid month. Must be between 1 and 12"
                    } else if (day < 1 || day > 31) {
                        binding.birthdayInputLayout.error = "Invalid day. Must be between 1 and 31"
                    } else {
                        binding.birthdayInputLayout.error = null
                    }
                }
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
    }
}
