package me.ashutoshkk.carecompass.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import me.ashutoshkk.carecompass.CheckboxClickListener
import me.ashutoshkk.carecompass.databinding.SymptomItemBinding
import me.ashutoshkk.carecompass.domain.model.Symptom

class SymptomRecyclerAdapter(val symptomList: List<Symptom>, private val checkboxClickListener: CheckboxClickListener) : RecyclerView.Adapter<SymptomRecyclerAdapter.ViewHolder>(),
    Filterable {

    private var filteredList = symptomList

    inner class ViewHolder(private val binding: SymptomItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(symptom: Symptom){
            binding.checkbox.text = symptom.name
            binding.checkbox
            binding.checkbox.isChecked = symptom.isPicked

            binding.checkbox.setOnClickListener {
                checkboxClickListener.onClick(!symptom.isPicked, symptom.id-1)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(SymptomItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return filteredList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(filteredList[position])
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val query = constraint.toString().lowercase()
                val results = FilterResults()
                val filtered = ArrayList<Symptom>()

                for (item in symptomList) {
                    if (item.name.lowercase().contains(query)) {
                        filtered.add(item)
                    }
                }

                results.count = filtered.size
                results.values = filtered

                return results
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredList = results?.values as ArrayList<Symptom>
                notifyDataSetChanged()
            }
        }
    }
}