package com.example.gallery_test.view.authorization

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.gallery_test.R
import com.example.gallery_test.databinding.FragmentLoginBinding
import com.example.gallery_test.factory.LoginViewModelFactory
import com.example.gallery_test.BaseFragment
import com.example.gallery_test.view.BottomNavigationFragment
import com.example.gallery_test.utils.toast
import com.example.gallery_test.App
import com.example.gallery_test.view.welcomepage.WelcomePageFragment
import javax.inject.Inject

class LoginFragment : BaseFragment() {

    private lateinit var binding: FragmentLoginBinding

    @Inject
    lateinit var loginViewModelFactory: LoginViewModelFactory

    private lateinit var loginViewModel: LoginViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as App).applicationComponent.inject(this)
        loginViewModel = ViewModelProvider(this, loginViewModelFactory)[LoginViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.loginButton.setOnClickListener {
            loginViewModel.login(binding.emailEditText.text.toString(), binding.passwordEditText.text.toString())
        }

        binding.signUpButton.setOnClickListener {
            replaceFragment(R.id.fragment_container, RegistrationFragment())
        }

        binding.cancelButton.setOnClickListener {
            replaceFragment(R.id.fragment_container, WelcomePageFragment())
        }

        setupObservers()
    }

    private fun setupObservers() {
        loginViewModel.loginLiveData.observe(viewLifecycleOwner) {
            when(it) {
                is LoginResponse.Error -> showError(it)
                is LoginResponse.Success -> loginSuccess()
            }
        }
    }

    private fun showError(message: LoginResponse.Error) {
        context?.toast(message.error.localizedMessage.orEmpty())
    }

    private fun loginSuccess() {
        replaceFragment(R.id.fragment_container, BottomNavigationFragment())
        context?.toast("Login successful")
    }
}
