package com.example.gallery_test.view.profile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gallery_test.BaseFragment
import com.example.gallery_test.R
import com.example.gallery_test.databinding.FragmentProfileBinding
import com.example.gallery_test.view.make.Room.AppDatabase

class ProfileFragment : BaseFragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var imageAdapter: ImageAdapter

    private val profileViewModel: ProfileViewModel by lazy {
        val imageDao = AppDatabase.getInstance(requireContext()).imageDao()
        val profileRepository = ProfileRepository(imageDao)
        ViewModelProvider(this, ProfileViewModelFactory(profileRepository)).get(ProfileViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imageAdapter = ImageAdapter(emptyList())

        setupRecyclerView()
        binding.settingTextView.setOnClickListener {
            replaceFragment(R.id.fragment_container, ProfileSettingFragment())
        }
        loadUserData()

        profileViewModel.setImageAdapter(imageAdapter)
        loadImagesFromDatabase()
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = imageAdapter
        }
    }

    private fun loadUserData() {
        val sharedPreferences = requireContext().getSharedPreferences("user_data", Context.MODE_PRIVATE)
        val username = sharedPreferences.getString("username", "") ?: ""
        val email = sharedPreferences.getString("email", "") ?: ""
        val birthday = sharedPreferences.getString("birthday", "") ?: ""
        val password = sharedPreferences.getString("password", "") ?: ""

        binding.birthTextView.text = birthday
    }

    private fun loadImagesFromDatabase() {
        profileViewModel.loadImagesFromDatabase()
    }
}
