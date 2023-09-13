package com.bignerdranch.android.geoquiz
import Question
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlin.math.roundToInt

private const val TAG = "QuizViewModel"
const val CURRENT_INDEX_KEY = "CURRENT_INDEX_KEY"
const val SCORE_KEY = "SCORE_KEY"

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

    private val userAnswers = IntArray(questionBank.size){-1}
    private var currentIndex: Int
        get() = savedStateHandle.get(CURRENT_INDEX_KEY) ?: 0
        set(value) = savedStateHandle.set(CURRENT_INDEX_KEY, value)

    private var score: Int
        get() = savedStateHandle.get(SCORE_KEY) ?: 0
        set(value) = savedStateHandle.set(SCORE_KEY, value)

    val currentQuestionAnswer: Boolean
        get() = questionBank[currentIndex].answer

    val currentQuestionUserAnswer: Int
        get() = userAnswers[currentIndex]

    val currentQuestionText: Int
        get() = questionBank[currentIndex].textResId

    val percentage: Float
        get() = ((score.toFloat() / questionBank.size.toFloat()) * 10000f).roundToInt() / 100f

    fun isFinished(): Boolean {
        for (userAnswer: Int in userAnswers) {
            if(userAnswer == -1) {
                return false
            }
        }
        return true
    }

    fun updateUserAnswer(answer: Boolean) {
        userAnswers[currentIndex] = if (answer) 1 else 0
    }
    fun moveToNext() {
        currentIndex = (currentIndex + 1) % questionBank.size
    }

    fun moveToPrev() {
        currentIndex = (currentIndex - 1) % questionBank.size
        if (currentIndex < 0){
            currentIndex += questionBank.size
        }
    }

    fun changeScore(value: Int){
        score += value;
    }
}