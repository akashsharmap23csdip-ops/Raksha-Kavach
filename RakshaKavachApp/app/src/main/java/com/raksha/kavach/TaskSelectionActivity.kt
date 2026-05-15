package com.raksha.kavach

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.raksha.kavach.data.TaskRepository

class TaskSelectionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_selection)

        val container = findViewById<LinearLayout>(R.id.taskContainer)

        for (task in TaskRepository.getTasks()) {
            val button = layoutInflater.inflate(
                R.layout.item_task_button,
                container,
                false
            ) as MaterialButton
            button.text = task.name
            button.setOnClickListener {
                val intent = Intent(this, PPEChecklistActivity::class.java)
                intent.putExtra(PPEChecklistActivity.EXTRA_TASK_NAME, task.name)
                startActivity(intent)
            }
            container.addView(button)
        }
    }
}
