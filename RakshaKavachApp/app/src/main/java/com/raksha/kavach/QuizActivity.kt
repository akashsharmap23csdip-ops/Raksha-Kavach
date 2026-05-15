package com.raksha.kavach

import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.raksha.kavach.data.QuizRepository
import com.raksha.kavach.data.ScoreRepository

class QuizActivity : AppCompatActivity() {
    private val questions = QuizRepository.getQuestions()
    private var index = 0

    private lateinit var textQuestion: TextView
    private lateinit var radioGroup: RadioGroup
    private lateinit var options: List<RadioButton>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        textQuestion = findViewById(R.id.textQuestion)
        radioGroup = findViewById(R.id.radioGroup)
        options = listOf(
            findViewById(R.id.rbOption1),
            findViewById(R.id.rbOption2),
            findViewById(R.id.rbOption3),
            findViewById(R.id.rbOption4)
        )

        findViewById<Button>(R.id.btnSubmit).setOnClickListener { handleSubmit() }
        findViewById<Button>(R.id.btnNext).setOnClickListener { nextQuestion() }

        bindQuestion()
    }

    private fun bindQuestion() {
        val question = questions[index]
        textQuestion.text = question.question
        radioGroup.clearCheck()
        options.forEachIndexed { i, radioButton ->
            radioButton.text = question.options[i]
        }
    }

    private fun handleSubmit() {
        val selectedId = radioGroup.checkedRadioButtonId
        if (selectedId == -1) {
            Toast.makeText(this, "Select an option", Toast.LENGTH_SHORT).show()
            return
        }

        val selectedIndex = options.indexOfFirst { it.id == selectedId }
        val question = questions[index]
        val isCorrect = selectedIndex == question.correctIndex

        if (isCorrect) {
            ScoreRepository(this).updateScore(2)
            Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Incorrect", Toast.LENGTH_SHORT).show()
        }
    }

    private fun nextQuestion() {
        index = (index + 1) % questions.size
        bindQuestion()
    }
}
