package com.example.gallery_test.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gallery_test.api.ApiServiceBuilder
import com.example.gallery_test.adapter.PhotoAdapter
import com.example.gallery_test.repository.HomeNewRepository
import com.example.gallery_test.viewModel.HomeNewViewModel
import com.example.gallery_test.databinding.FragmentHomeNewBinding
import io.reactivex.rxjava3.disposables.CompositeDisposable

class HomeNewFragment : Fragment() {
    private var _binding: FragmentHomeNewBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PhotoAdapter
    private lateinit var homeNewViewModel: HomeNewViewModel
    private val compositeDisposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeNewBinding.inflate(inflater, container, false)
        val view = binding.root

        recyclerView = binding.recyclerView
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        adapter = PhotoAdapter()
        recyclerView.adapter = adapter

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        fetchPhotos()
    }

    private fun setupViewModel() {
        val apiService = ApiServiceBuilder.createApiService()
        val repository = HomeNewRepository(apiService)
        val viewModelFactory = HomeNewViewModelFactory(repository)

        homeNewViewModel = ViewModelProvider(this, viewModelFactory).get(HomeNewViewModel::class.java)

        homeNewViewModel.photoList.observe(viewLifecycleOwner, Observer { photos ->
            adapter.submitList(photos.data)
        })

        homeNewViewModel.error.observe(viewLifecycleOwner, Observer { errorMessage ->
            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
        })
    }

    private fun fetchPhotos() {
        homeNewViewModel.fetchPhotos(true, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        compositeDisposable.clear()
    }
}
