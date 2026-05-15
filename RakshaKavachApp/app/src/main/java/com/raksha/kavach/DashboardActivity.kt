package com.raksha.kavach

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class DashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        findViewById<Button>(R.id.btnStartSafety).setOnClickListener {
            startActivity(Intent(this, TaskSelectionActivity::class.java))
        }
        findViewById<Button>(R.id.btnDailyQuiz).setOnClickListener {
            startActivity(Intent(this, QuizActivity::class.java))
        }
        findViewById<Button>(R.id.btnReportIncident).setOnClickListener {
            startActivity(Intent(this, IncidentReportActivity::class.java))
        }
        findViewById<Button>(R.id.btnSafetyScore).setOnClickListener {
            startActivity(Intent(this, SafetyScoreActivity::class.java))
        }
        findViewById<Button>(R.id.btnEmergencyCall).setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:112")
            startActivity(intent)
        }
    }
}
