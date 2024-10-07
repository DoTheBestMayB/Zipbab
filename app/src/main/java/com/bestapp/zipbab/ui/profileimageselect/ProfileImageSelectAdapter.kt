package com.bestapp.zipbab.ui.profileimageselect

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.bestapp.zipbab.databinding.ItemProfileImageSelectGalleryBinding
import com.bestapp.zipbab.model.GalleryImage

class ProfileImageSelectAdapter(
    private val onClick: (GalleryImage) -> Unit,
) : PagingDataAdapter<GalleryImage, ProfileImageSelectAdapter.ProfileImageSelectViewHolder>(diff) {

    class ProfileImageSelectViewHolder(
        private val binding: ItemProfileImageSelectGalleryBinding,
        private val onClick: (GalleryImage) -> Unit,
    ) : ViewHolder(binding.root) {

        fun bind(item: GalleryImage) {
            binding.root.setOnClickListener {
                onClick(item)
            }
            binding.ivThumbnail.load(item.uri)
            binding.tvName.text = item.name
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProfileImageSelectViewHolder {
        return ProfileImageSelectViewHolder(
            ItemProfileImageSelectGalleryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onClick,
        )
    }

    override fun onBindViewHolder(holder: ProfileImageSelectViewHolder, position: Int) {
        val item = getItem(position) ?: return
        holder.bind(item)
    }

    companion object {
        private val diff = object : DiffUtil.ItemCallback<GalleryImage>() {
            override fun areItemsTheSame(
                oldItem: GalleryImage,
                newItem: GalleryImage
            ): Boolean {
                return oldItem.uri == newItem.uri
            }

            override fun areContentsTheSame(
                oldItem: GalleryImage,
                newItem: GalleryImage
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}