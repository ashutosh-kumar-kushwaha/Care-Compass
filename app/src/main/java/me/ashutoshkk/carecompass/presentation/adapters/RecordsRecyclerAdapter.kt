package me.ashutoshkk.carecompass.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import me.ashutoshkk.carecompass.databinding.RecordItemBinding
import me.ashutoshkk.carecompass.domain.model.Record

class RecordsRecyclerAdapter(private val recordList : List<Record>) : RecyclerView.Adapter<RecordsRecyclerAdapter.RecordViewHolder>(){

    inner class RecordViewHolder(private val binding: RecordItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(record: Record){
            binding.clinicNameTxtVw.text = record.name
            binding.locationTxtVw.text = record.location
            binding.fromTxtVw.text = record.from
            binding.toTxtVw.text = record.to
            binding.symptomsRecyclerView.adapter = TextRecyclerView(record.symptoms.split(",").toTypedArray().toList())
            binding.symptomsRecyclerView.layoutManager = GridLayoutManager(binding.root.context, 2)
            binding.descriptionTxtVw.text = record.description
            binding.prescriptionsRecyclerView.adapter = ImageRecyclerAdapter(record.prescriptionUrls)
            binding.prescriptionsRecyclerView.layoutManager = LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordViewHolder {
        return RecordViewHolder(RecordItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return recordList.size
    }

    override fun onBindViewHolder(holder: RecordViewHolder, position: Int) {
        holder.bind(recordList[position])
    }
}