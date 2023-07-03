package com.example.gallery_test.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gallery_test.model.PhotoItem
import com.example.gallery_test.R

class PhotoAdapter : RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder>() {
    private val photoList: MutableList<PhotoItem> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.photo_list, parent, false)
        return PhotoViewHolder(view)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val photoItem = photoList[position]
        holder.bind(photoItem)
    }

    override fun getItemCount(): Int {
        return photoList.size
    }

    fun submitList(list: List<PhotoItem>) {
        photoList.clear()
        photoList.addAll(list)
        notifyDataSetChanged()
    }

    inner class PhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val photoImageView: ImageView = itemView.findViewById(R.id.photoImageView)

        fun bind(photoItem: PhotoItem) {
            val imageName = photoItem.image?.name
            if (imageName != null) {
                Glide.with(itemView)
                    .load("https://gallery.prod1.webant.ru/media/$imageName")
                    .into(photoImageView)
            }
        }

    }
}
