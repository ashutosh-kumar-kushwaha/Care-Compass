package me.ashutoshkk.carecompass.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import me.ashutoshkk.carecompass.databinding.TextItemBinding

class TextRecyclerView(private val list: List<String>) : RecyclerView.Adapter<TextRecyclerView.ViewHolder>() {
    inner class ViewHolder(private val binding: TextItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(text: String, position: Int){
            val t = "$position. $text"
            binding.textView.text = t
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(TextItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position], position+1)
    }
}