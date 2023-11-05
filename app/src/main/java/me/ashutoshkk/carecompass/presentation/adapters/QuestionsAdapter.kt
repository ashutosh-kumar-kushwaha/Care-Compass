package me.ashutoshkk.carecompass.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import me.ashutoshkk.carecompass.databinding.QuestionItemBinding
import me.ashutoshkk.carecompass.domain.model.Question

class QuestionsAdapter(private val questions: List<Question>, private val clickListener: QuestionClickListener): RecyclerView.Adapter<QuestionsAdapter.QuestionViewHolder>() {
    inner class QuestionViewHolder(private val binding: QuestionItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(question: Question){
            val questionNo = "Question ${question.id}"
            binding.questionNoTxtVw.text = questionNo
            binding.questionTxtVw.text = question.question
            binding.optionARBtn.text = question.optionA
            binding.optionBRBtn.text = question.optionB
            binding.optionCRBtn.text = question.optionC
            binding.optionDRBtn.text = question.optionD
            val ids = listOf<Int>(binding.optionARBtn.id, binding.optionBRBtn.id, binding.optionCRBtn.id, binding.optionDRBtn.id)
            binding.radioGroup.setOnCheckedChangeListener{radioGroup, index ->
                clickListener.onClick(adapterPosition, radioGroup.checkedRadioButtonId)
            }
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

    interface QuestionClickListener{
        fun onClick(position: Int, answer: Int)
    }
}