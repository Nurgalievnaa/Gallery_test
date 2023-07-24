package com.example.gallery_test.view.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.gallery_test.R
import com.example.gallery_test.view.make.Room.ImageEntity

class ImageAdapter(private var images: List<ImageEntity>) : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(images[position])
    }

    override fun getItemCount(): Int {
        return images.size
    }

    fun updateData(newImages: List<ImageEntity>) {
        images = newImages
        notifyDataSetChanged()
    }

    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.imageView)

        fun bind(image: ImageEntity) {
            // Load the image into the ImageView using Glide or any other image loading library
            // For example, if you are using Glide:
            // Glide.with(itemView.context).load(image.imageUri).into(imageView)
        }
    }
}
