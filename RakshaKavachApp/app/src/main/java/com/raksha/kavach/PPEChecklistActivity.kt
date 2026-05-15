package com.raksha.kavach

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.raksha.kavach.data.ScoreRepository
import com.raksha.kavach.data.TaskRepository
import com.raksha.kavach.utils.RiskCalculator
import com.raksha.kavach.utils.RiskLevel

class PPEChecklistActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_TASK_NAME = "extra_task_name"
        const val EXTRA_RISK_LEVEL = "extra_risk_level"
        const val EXTRA_MISSING_LIST = "extra_missing_list"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ppe_checklist)

        val taskName = intent.getStringExtra(EXTRA_TASK_NAME) ?: "Task"
        val task = TaskRepository.getTaskByName(taskName)
        val required = task?.requiredPpe ?: listOf("Helmet", "Gloves", "Boots", "Goggles", "Face Shield")

        findViewById<TextView>(R.id.textTaskName).text = taskName
        findViewById<TextView>(R.id.textRequiredPpe).text = required.joinToString(", ")

        val cbHelmet = findViewById<CheckBox>(R.id.cbHelmet)
        val cbGloves = findViewById<CheckBox>(R.id.cbGloves)
        val cbBoots = findViewById<CheckBox>(R.id.cbBoots)
        val cbGoggles = findViewById<CheckBox>(R.id.cbGoggles)
        val cbFaceShield = findViewById<CheckBox>(R.id.cbFaceShield)

        val selected = mapOf(
            "Helmet" to cbHelmet,
            "Gloves" to cbGloves,
            "Boots" to cbBoots,
            "Goggles" to cbGoggles,
            "Face Shield" to cbFaceShield
        )

        findViewById<Button>(R.id.btnCheckRisk).setOnClickListener {
            val missing = required.filter { item ->
                val checkBox = selected[item]
                checkBox == null || !checkBox.isChecked
            }

            val riskLevel = RiskCalculator.calculateRisk(missing.size)
            ScoreRepository(this).updateScore(RiskCalculator.scoreDelta(riskLevel))

            val intent = Intent(this, RiskResultActivity::class.java)
            intent.putExtra(EXTRA_TASK_NAME, taskName)
            intent.putExtra(EXTRA_RISK_LEVEL, riskLevel.name)
            intent.putStringArrayListExtra(EXTRA_MISSING_LIST, ArrayList(missing))
            startActivity(intent)
        }
    }
}
