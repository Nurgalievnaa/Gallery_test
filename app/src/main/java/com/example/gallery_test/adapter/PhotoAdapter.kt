package com.example.gallery_test.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gallery_test.model.PhotoItem
import com.example.gallery_test.R

class PhotoAdapter(private val photoItemClickListener: OnPhotoItemClickListener) :
    ListAdapter<PhotoItem, PhotoAdapter.PhotoViewHolder>(DiffCallback()) {

    interface OnPhotoItemClickListener {
        fun onPhotoItemClick(photoItem: PhotoItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.photo_list, parent, false)
        return PhotoViewHolder(view)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val photoItem = getItem(position)
        holder.bind(photoItem)
    }

    inner class PhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val photoImageView: ImageView = itemView.findViewById(R.id.photoImageView)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val photoItem = getItem(position)
                    photoItemClickListener.onPhotoItemClick(photoItem)
                }
            }
        }

        fun bind(photoItem: PhotoItem) {
            val imageName = photoItem.image?.name
            if (imageName != null) {
                Glide.with(itemView)
                    .load("https://gallery.prod1.webant.ru/media/$imageName")
                    .into(photoImageView)
            }
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<PhotoItem>() {
        override fun areItemsTheSame(oldItem: PhotoItem, newItem: PhotoItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PhotoItem, newItem: PhotoItem): Boolean {
            return oldItem == newItem
        }
    }
}
