package com.bestapp.zipbab.ui.recruitment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bestapp.zipbab.R
import com.bestapp.zipbab.databinding.ItemRecruitStepBinding

class StepAdapter: ListAdapter<StepInfo, StepAdapter.StepViewHolder>(diff) {

    class StepViewHolder(private val binding: ItemRecruitStepBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: StepInfo) {
            val colorRes = if (item.isProcessed) {
                R.color.black
            } else {
                R.color.clr_D3D3D3
            }
            binding.v.setBackgroundResource(colorRes)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StepViewHolder {
        return StepViewHolder(
            ItemRecruitStepBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: StepViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val diff = object: DiffUtil.ItemCallback<StepInfo>() {
            override fun areItemsTheSame(oldItem: StepInfo, newItem: StepInfo): Boolean {
                return oldItem.step == newItem.step
            }

            override fun areContentsTheSame(oldItem: StepInfo, newItem: StepInfo): Boolean {
                return oldItem == newItem
            }
        }
    }
}