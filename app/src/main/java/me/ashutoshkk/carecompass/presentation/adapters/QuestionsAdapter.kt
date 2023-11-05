package me.ashutoshkk.carecompass.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import me.ashutoshkk.carecompass.databinding.QuestionItemBinding
import me.ashutoshkk.carecompass.domain.model.Question

class QuestionsAdapter(private val questions: List<Question>): RecyclerView.Adapter<QuestionsAdapter.QuestionViewHolder>() {
    class QuestionViewHolder(private val binding: QuestionItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(question: Question){

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        return QuestionViewHolder(QuestionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return questions.size
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        holder.bind(questions[position])
    }
}