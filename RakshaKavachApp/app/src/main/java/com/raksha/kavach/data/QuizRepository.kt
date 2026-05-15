package com.raksha.kavach.data

object QuizRepository {
    private val questions = listOf(
        QuizQuestion(
            "Which gear is compulsory for welding?",
            listOf("Helmet", "Boots", "Mask", "Apron"),
            0
        ),
        QuizQuestion(
            "What color indicates high risk?",
            listOf("Green", "Yellow", "Red", "Blue"),
            2
        ),
        QuizQuestion(
            "Which PPE protects eyes?",
            listOf("Gloves", "Goggles", "Helmet", "Boots"),
            1
        )
    )

    fun getQuestions(): List<QuizQuestion> = questions
}
