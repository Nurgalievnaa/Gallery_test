package com.example.gallery_test.view.welcomepage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.gallery_test.BaseFragment
import com.example.gallery_test.R
import com.example.gallery_test.databinding.FragmentWelcomePageBinding
import com.example.gallery_test.view.authorization.LoginFragment
import com.example.gallery_test.view.authorization.RegistrationFragment

class WelcomePageFragment : BaseFragment() {
    private lateinit var binding: FragmentWelcomePageBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentWelcomePageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)

        loginButton.setOnClickListener {
            replaceFragment(R.id.fragment_container, LoginFragment())
        }

        registrationButton.setOnClickListener {
            replaceFragment(R.id.fragment_container,RegistrationFragment())
        }
    }
}
