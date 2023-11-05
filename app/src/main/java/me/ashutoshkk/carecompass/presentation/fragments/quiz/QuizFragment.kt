package me.ashutoshkk.carecompass.presentation.fragments.quiz

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import me.ashutoshkk.carecompass.databinding.FragmentQuizBinding
import me.ashutoshkk.carecompass.domain.model.Question
import me.ashutoshkk.carecompass.presentation.adapters.QuestionsAdapter

class QuizFragment : Fragment(), QuestionsAdapter.QuestionClickListener {

    private var _binding : FragmentQuizBinding? = null
    private val binding: FragmentQuizBinding get() = _binding!!

//    private val questions = listOf<Question>(
//        Question(1, "Question 1", "Option A", "Option B", "Option C", "Option D"),
//        Question(2, "Question 1", "Option A", "Option B", "Option C", "Option D"),
//        Question(3, "Question 1", "Option A", "Option B", "Option C", "Option D"),
//        Question(4, "Question 1", "Option A", "Option B", "Option C", "Option D"),
//        Question(5, "Question 1", "Option A", "Option B", "Option C", "Option D")
//    )
//
//    private val questionsAdapter by lazy {
//        QuestionsAdapter(questions, this)
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuizBinding.inflate(inflater, container, false)

//        binding.questionsRecyclerView.adapter = questionsAdapter

        binding.webView.loadUrl("www.google.com")
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.webViewClient = WebViewClient()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(position: Int, answer: Int) {
        Log.d("Ashu", "$position , $answer")
    }
}