package com.raksha.kavach.data

data class QuizQuestion(
    val question: String,
    val options: List<String>,
    val correctIndex: Int
)
