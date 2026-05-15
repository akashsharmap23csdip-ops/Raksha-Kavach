package com.raksha.kavach

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.raksha.kavach.data.ScoreRepository

class SafetyScoreActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_safety_score)

        val score = ScoreRepository(this).getScore()
        findViewById<TextView>(R.id.textSafetyScore).text = score.toString()
    }
}
