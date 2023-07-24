package com.example.gallery_test.view.home

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.gallery_test.BaseFragment
import com.example.gallery_test.databinding.FragmentPhotoDetailBinding
import com.example.gallery_test.model.PhotoItem

class PhotoDetailFragment : BaseFragment() {

    private lateinit var binding: FragmentPhotoDetailBinding
    private lateinit var viewModel: PhotoDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPhotoDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(PhotoDetailViewModel::class.java)
        viewModel.photoItem.observe(viewLifecycleOwner, Observer { photoItem ->
            photoItem?.let {
                val imageUri = Uri.parse("https://gallery.prod1.webant.ru/media/${it.image.name}")
                binding.imageView.setImageURI(imageUri)

                binding.nameTextView.text = it.name
                binding.descriptionTextView.text = it.description
            }
        })

        val photoItem: PhotoItem? = arguments?.getParcelable("photoItem")
        photoItem?.let { viewModel.setPhotoItem(it) }
    }
}
