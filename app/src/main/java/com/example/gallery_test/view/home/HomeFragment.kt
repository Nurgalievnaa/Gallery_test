package com.example.gallery_test.view.home

import android.content.Context
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
import com.example.gallery_test.App
import com.example.gallery_test.BaseFragment
import com.example.gallery_test.R
import com.example.gallery_test.adapter.PhotoAdapter
import com.example.gallery_test.factory.HomeViewModelFactory
import com.example.gallery_test.model.PhotoItem
import javax.inject.Inject

class HomeFragment(private val isNew: Boolean, private val isPopular: Boolean) : BaseFragment() {
    @Inject
    lateinit var homeViewModelFactory: HomeViewModelFactory

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PhotoAdapter
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var homePageFragment: HomePageFragment

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as App).applicationComponent.inject(this)
        homeViewModel = ViewModelProvider(this, homeViewModelFactory)[HomeViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home_popular, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        adapter = PhotoAdapter(onPhotoItemClickListener)
        recyclerView.adapter = adapter

        setupViewModel()
        fetchPhotos()

        homePageFragment = parentFragment as HomePageFragment
        val searchView = homePageFragment.binding?.searchView
        searchView?.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                performSearch(newText.orEmpty())
                return true
            }
        })
    }

    private val onPhotoItemClickListener = object : PhotoAdapter.OnPhotoItemClickListener {
        override fun onPhotoItemClick(photoItem: PhotoItem) {
            openPhotoDetailFragment(photoItem)
        }
    }

    private fun performSearch(query: String) {
        val filteredPhotos = homeViewModel.photoList.value?.data?.filter { photo ->
            photo.name.contains(query, ignoreCase = true)
        }
        if (filteredPhotos != null) {
            adapter.submitList(filteredPhotos)
        }
    }

    private fun setupViewModel() {
        homeViewModel.photoList.observe(viewLifecycleOwner, Observer { photos ->
            adapter.submitList(photos.data)
        })

        homeViewModel.error.observe(viewLifecycleOwner, Observer { errorMessage ->
            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
        })
    }

    private fun openPhotoDetailFragment(photoItem: PhotoItem) {
        val photoDetailFragment = PhotoDetailFragment()
        val bundle = Bundle()
        bundle.putParcelable("photoItem", photoItem)
        photoDetailFragment.arguments = bundle
        replaceFragment(R.id.fragment_container,photoDetailFragment)
    }

    private fun fetchPhotos() {
        homeViewModel.fetchPhotos(isNew, isPopular)
    }
}
