package com.example.gallery_test.view.make

import android.Manifest
import android.content.ContentValues
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.gallery_test.BaseFragment
import com.example.gallery_test.R
import com.example.gallery_test.databinding.FragmentMakeBinding
import com.example.gallery_test.factory.MakeViewModelFactory
import com.example.gallery_test.utils.toast
import com.example.gallery_test.view.BottomNavigationFragment
import com.example.gallery_test.view.make.Room.AppDatabase
import com.example.gallery_test.view.make.Room.ImageDao
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.launch

class MakeFragment : BaseFragment() {
    private var _binding: FragmentMakeBinding? = null
    private val binding get() = _binding!!

    private lateinit var imageDao: ImageDao
    private lateinit var makeViewModel: MakeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val appDatabase = AppDatabase.getInstance(requireContext())
        imageDao = appDatabase.imageDao()

        val makeViewModelFactory = MakeViewModelFactory(imageDao)
        makeViewModel = viewModels<MakeViewModel> { makeViewModelFactory }.value
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backTV.setOnClickListener {
            replaceFragment(R.id.fragmentContainer, BottomNavigationFragment())
        }
    }

    private val pickMedia =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            if (uri != null) {
                val imageUri = saveImageToGallery(uri)
                if (imageUri != null) {
                    makeViewModel.setCapturedPhotoUri(imageUri)
                    displayImage(imageUri)
                    Log.d("akma1", imageUri.toString())
                } else {
                    context?.toast("Failed to save image")
                }
            }
        }

    private val takePicture =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { isImageSaved: Boolean ->
            if (isImageSaved) {
                val imageUri = makeViewModel.capturedPhotoUri.value
                if (imageUri != null) {
                    displayImage(imageUri)
                    Log.d("akma", imageUri.toString())
                } else {
                    context?.toast("Image URI is null")
                }
            } else {
                context?.toast("Failed to save image")
            }
        }

    private lateinit var profileImageView: ImageView

    private val requestCameraPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                launchCamera()
            } else {
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMakeBinding.inflate(inflater, container, false)
        val view = binding.root
        profileImageView = binding.profileImageView

        binding.changePhotoButton.setOnClickListener {
            showChangePhotoBottomSheet()
        }

        binding.addButton.setOnClickListener {
            saveImageToDatabase()
        }
        return view
    }

    private fun showChangePhotoBottomSheet() {
        val bottomSheet = BottomSheetDialog(requireContext())
        val bottomSheetView = layoutInflater.inflate(R.layout.fragment_bottom_sheet_dialog, null)

        bottomSheet.setContentView(bottomSheetView)
        bottomSheet.show()

        val takePhotoButton = bottomSheetView.findViewById<Button>(R.id.cameraOption)
        val chooseGalleryButton = bottomSheetView.findViewById<Button>(R.id.galleryOption)

        takePhotoButton.setOnClickListener {
            bottomSheet.dismiss()
            checkCameraPermission()
        }

        chooseGalleryButton.setOnClickListener {
            bottomSheet.dismiss()
            launchPhotoPicker()
        }
    }

    private fun checkCameraPermission() {
        val cameraPermission = Manifest.permission.CAMERA
        if (ContextCompat.checkSelfPermission(requireContext(), cameraPermission) == PackageManager.PERMISSION_GRANTED
        ) {
            launchCamera()
        } else {
            requestCameraPermissionLauncher.launch(cameraPermission)
        }
    }

    private fun launchCamera() {
        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "Image_${System.currentTimeMillis()}.jpg")
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        }
        val contentResolver = requireContext().contentResolver
        val imageUri =
            contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

        imageUri?.let { uri ->
            makeViewModel.setCapturedPhotoUri(uri)
            takePicture.launch(uri)
        } ?: run {
            context?.toast("Failed to create image file")
        }
    }

    private fun launchPhotoPicker() {
        pickMedia.launch("image/*")
    }

    private fun displayImage(uri: Uri) {
        profileImageView.setImageURI(uri)
    }

    private fun saveImageToGallery(imageUri: Uri): Uri? {
        val sourceBitmap =
            MediaStore.Images.Media.getBitmap(requireContext().contentResolver, imageUri)
        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "Image_${System.currentTimeMillis()}.jpg")
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        }
        val contentResolver = requireContext().contentResolver
        val uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

        uri?.let { savedImageUri ->
            contentResolver.openOutputStream(savedImageUri)?.use { outputStream ->
                sourceBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                outputStream.close()
                return savedImageUri
            }
        }
        return null
    }

    private fun saveImageToDatabase() {
        val name = binding.nameEditText.text?.toString()
        val description = binding.descriptionEditText.text?.toString()
        val uri = makeViewModel.capturedPhotoUri.value

        if (!name.isNullOrEmpty() && !description.isNullOrEmpty() && uri != null) {
            makeViewModel.setName(name)
            makeViewModel.setDescription(description)

            lifecycleScope.launch {
                makeViewModel.saveImageToDatabase(name, description, uri)
                clearFields()
                context?.toast("Image saved to database")
            }
        } else {
            context?.toast("Please fill in all fields")
        }
    }

    private fun clearFields() {
        binding.nameEditText.text = null
        binding.descriptionEditText.text = null
        profileImageView.setImageDrawable(null)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
