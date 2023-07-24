package com.example.gallery_test.view.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.gallery_test.BaseFragment
import com.example.gallery_test.R
import com.example.gallery_test.databinding.FragmentProfileSettingBinding
import com.example.gallery_test.view.BottomNavigationFragment

class ProfileSettingFragment : BaseFragment() {
    private lateinit var binding: FragmentProfileSettingBinding
    private val profileViewModel: ProfileViewModel by viewModels({ requireParentFragment() })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val username = arguments?.getString("username") ?: ""
        val email = arguments?.getString("email") ?: ""
        val birthday = arguments?.getString("birthday") ?: ""
        val password = arguments?.getString("password") ?: ""

        binding.cancelTV.setOnClickListener {
            replaceFragment(R.id.fragment_container, BottomNavigationFragment())
        }

        binding.saveTV.setOnClickListener {
            saveUserData()
            replaceFragment(R.id.fragment_container, BottomNavigationFragment())
        }
    }

    private fun saveUserData() {
        val username = binding.userNameEditText.text.toString()
        val email = binding.emailEditText.text.toString()
        val birthday = binding.birthEditText.text.toString()
        val oldPassword = binding.oldPasswordEditText.text.toString()
        val newPassword = binding.newPasswordEditText.text.toString()
        val confirmPassword = binding.confirmPassEditText.text.toString()

        profileViewModel.updateUserData(username, email, birthday)

        binding.oldPasswordEditText.text = null
        binding.newPasswordEditText.text = null
        binding.confirmPassEditText.text = null
    }
}
