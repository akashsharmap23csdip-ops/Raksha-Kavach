package com.raksha.kavach.data

object TaskRepository {
    private val tasks = listOf(
        Task("Welding", listOf("Helmet", "Gloves", "Goggles"), "High"),
        Task("Electrical Work", listOf("Helmet", "Gloves", "Boots"), "High"),
        Task("Height Work", listOf("Helmet", "Boots", "Face Shield"), "High"),
        Task("Digging Trench", listOf("Helmet", "Boots", "Gloves"), "Medium"),
        Task("Chemical Handling", listOf("Gloves", "Goggles", "Face Shield"), "High")
    )

    fun getTasks(): List<Task> = tasks

    fun getTaskByName(name: String): Task? = tasks.find { it.name == name }
}
