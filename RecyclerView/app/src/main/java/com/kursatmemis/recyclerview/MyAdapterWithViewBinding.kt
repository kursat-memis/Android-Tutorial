package com.kursatmemis.recyclerview

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.kursatmemis.recyclerview.databinding.ItemCardViewBinding


class MyAdapterWithViewBinding(
    private val dataSource: ArrayList<String>,
    val itemOnClickListener: ItemOnClickListener
) :
    RecyclerView.Adapter<MyAdapterWithViewBinding.MyViewHolder>() {

    interface ItemOnClickListener {
        fun onClick(text: String, adapterPosition: Int, layoutPosition: Int)
    }

    inner class MyViewHolder(val binding: ItemCardViewBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                itemOnClickListener.onClick(dataSource[layoutPosition], adapterPosition, layoutPosition)
            }
        }

        fun bind(text: String) {
            binding.textView.text = text
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemCardViewBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // Item'daki view'lara direkt aşağıdaki gibi bir atama yapmak yerine, ViewHolder'daki
        // bind methodunu kullanarak atama yapmamız daha okunabilir bir kod sağlar.
        // holder.binding.textView.text = dataSource[position]

        holder.binding.root.setOnClickListener {
            Log.w("mKm - krst", "lp:${holder.layoutPosition} ap: ${holder.adapterPosition} position: $position")
            dataSource.add(0, "New Element")
            notifyItemInserted(0)
            Log.w("mKm - krst", "lp:${holder.layoutPosition} ap: ${holder.adapterPosition} position: $position")
        }
        // 2 - 2
        // 3 - 3
        val text = dataSource[position]
        holder.bind(text)
    }



    override fun getItemCount(): Int {
        return dataSource.size
    }

    fun updateAdapter(newList: ArrayList<String>) {
        dataSource.clear()
        dataSource.addAll(newList)
        notifyDataSetChanged()
    }

}
