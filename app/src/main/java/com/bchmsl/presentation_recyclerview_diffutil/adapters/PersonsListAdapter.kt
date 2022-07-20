package com.bchmsl.presentation_recyclerview_diffutil.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bchmsl.presentation_recyclerview_diffutil.databinding.LayoutPersonNormalBinding
import com.bchmsl.presentation_recyclerview_diffutil.databinding.LayoutPersonPhotoBinding
import com.bchmsl.presentation_recyclerview_diffutil.extensions.setColor
import com.bchmsl.presentation_recyclerview_diffutil.extensions.setFemaleImage
import com.bchmsl.presentation_recyclerview_diffutil.extensions.setMaleImage
import com.bchmsl.presentation_recyclerview_diffutil.model.Gender
import com.bchmsl.presentation_recyclerview_diffutil.model.Person

class PersonsListAdapter : ListAdapter<Person, RecyclerView.ViewHolder>(PersonItemCallback()) {
    companion object {
        const val PERSON_NORMAL = 1
        const val PERSON_PHOTO = 2
    }
    var itemClick: ((Person) -> Unit)? = null

    inner class NormalPersonViewHolder(private val binding: LayoutPersonNormalBinding) :
        RecyclerView.ViewHolder(binding.root){
        fun bind() {
            val currentItem = getItem(adapterPosition)
            binding.apply {
                root.setColor(currentItem)
                val name = "${currentItem.firstName} ${currentItem.lastName}"
                tvName.text = name
                tvEmail.text = currentItem.email
                tvId.text = currentItem.id.toString()
                root.setOnClickListener { itemClick?.invoke(currentItem) }
            }
        }
        }

    inner class PhotoPersonViewHolder(private val binding: LayoutPersonPhotoBinding) :
        RecyclerView.ViewHolder(binding.root){
        fun bind() {
            val currentItem = getItem(adapterPosition)
                binding.apply {
                root.setColor(currentItem)
                val name = "${currentItem.firstName} ${currentItem.lastName}"
                tvName.text = name
                tvEmail.text = currentItem.email
                tvId.text = currentItem.id.toString()
                when(currentItem.gender){
                    Gender.MALE -> binding.civProfilePicture.setMaleImage(currentItem.photo)
                    Gender.FEMALE -> binding.civProfilePicture.setFemaleImage(currentItem.photo)
                }
                    root.setOnClickListener { itemClick?.invoke(currentItem) }
                }
        }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            PERSON_PHOTO -> PhotoPersonViewHolder(
                LayoutPersonPhotoBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )
            else -> {
                NormalPersonViewHolder(
                    LayoutPersonNormalBinding.inflate(
                        LayoutInflater.from(
                            parent.context
                        ), parent, false
                    )
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder){
            is NormalPersonViewHolder -> holder.bind()
            is PhotoPersonViewHolder -> holder.bind()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position).photo) {
            null -> PERSON_NORMAL
            else -> PERSON_PHOTO
        }
    }

    class PersonItemCallback:DiffUtil.ItemCallback<Person>(){
        override fun areItemsTheSame(oldItem: Person, newItem: Person) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Person, newItem: Person) =
            oldItem == newItem
    }
}