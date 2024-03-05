package com.kursatmemis.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kursatmemis.recyclerview.databinding.ViewType1Binding
import com.kursatmemis.recyclerview.databinding.ViewType2Binding

class MultiViewAdapter(val dataSource: ArrayList<String>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class ViewHolderOfViewType1(private val binding: ViewType1Binding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(text: String) {
            binding.textView1.text = text
        }
    }

    inner class ViewHolderOfViewType2(private val binding: ViewType2Binding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(text: String) {
            binding.textView2.text = text
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 1) {
            ViewHolderOfViewType1(
                ViewType1Binding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        } else {
            ViewHolderOfViewType2(
                ViewType2Binding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == 1) {
            (holder as ViewHolderOfViewType1).bind(dataSource[position])
        } else {
            (holder as ViewHolderOfViewType2).bind(dataSource[position])
        }
    }

    override fun getItemCount(): Int {
        return dataSource.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (position % 2 == 0) 1 else 0
    }
}