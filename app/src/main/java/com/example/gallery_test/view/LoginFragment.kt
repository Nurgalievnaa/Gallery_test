package com.example.gallery_test.view

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.gallery_test.R
import com.example.gallery_test.api.ApiServiceBuilder
import com.example.gallery_test.databinding.FragmentLoginBinding
import com.example.gallery_test.viewModel.LoginViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var loginViewModel: LoginViewModel
    private val compositeDisposable = CompositeDisposable()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPreferences =
            requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        val apiService = ApiServiceBuilder.createApiService()

        loginViewModel =
            ViewModelProvider(
                this,
                LoginViewModelFactory(apiService, sharedPreferences)
            )[LoginViewModel::class.java]

        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            //TODO move subscribe to viewModel
            val disposable = loginViewModel.login(email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ loginSuccessful ->
                    if (loginSuccessful) {
                        parentFragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, BottomNavigationFragment())
                            .commit()
                        //TODO move to method
                        Toast.makeText(
                            requireContext(),
                            "Login Successful",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Invalid username or password",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }, { error ->
                    Toast.makeText(
                        requireContext(),
                        "Error: ${error.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                })

            compositeDisposable.add(disposable)
        }

        binding.signUpButton.setOnClickListener {
            //TODO move navigation to method
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, RegistrationFragment())
                .commit()
        }
        //TODO remove useless variable
        val cancel = binding.cancelButton
        cancel.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, WelcomePageFragment())
                .commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        compositeDisposable.dispose()
    }
}
