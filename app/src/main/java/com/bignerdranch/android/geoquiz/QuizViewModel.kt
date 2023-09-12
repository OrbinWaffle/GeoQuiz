package com.bignerdranch.android.geoquiz
import Question
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

private const val TAG = "QuizViewModel"
const val CURRENT_INDEX_KEY = "CURRENT_INDEX_KEY"

class QuizViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {

    private val questionBank = listOf(
        Question(R.string.question_china, true),
        Question(R.string.question_russia, false),
        Question(R.string.question_antarctica, true),
        Question(R.string.question_india, true),
        Question(R.string.question_japan, false),
        Question(R.string.question_france, false),
        Question(R.string.question_canada, true)
    )

    private var currentIndex: Int
        get() = savedStateHandle.get(CURRENT_INDEX_KEY) ?: 0
        set(value) = savedStateHandle.set(CURRENT_INDEX_KEY, value)

    val currentQuestionAnswer: Boolean
        get() = questionBank[currentIndex].answer

    val currentQuestionText: Int
        get() = questionBank[currentIndex].textResId

    fun moveToNext() {
        currentIndex = (currentIndex + 1) % questionBank.size
    }

    fun moveToPrev() {
        currentIndex = (currentIndex - 1) % questionBank.size
        if (currentIndex < 0){
            currentIndex += questionBank.size;
        }
    }
}