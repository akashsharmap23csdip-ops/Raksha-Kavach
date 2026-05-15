package com.raksha.kavach

import android.content.Intent
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.raksha.kavach.utils.RiskLevel
import java.util.Locale

class RiskResultActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private var textToSpeech: TextToSpeech? = null
    private var pendingMessage: String? = null
    private var shouldAutoSpeak = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_risk_result)

        textToSpeech = TextToSpeech(this, this)

        val riskName = intent.getStringExtra(PPEChecklistActivity.EXTRA_RISK_LEVEL) ?: RiskLevel.MEDIUM.name
        val riskLevel = try {
            RiskLevel.valueOf(riskName)
        } catch (_: IllegalArgumentException) {
            RiskLevel.MEDIUM
        }
        val missingList = intent.getStringArrayListExtra(PPEChecklistActivity.EXTRA_MISSING_LIST) ?: arrayListOf()

        val textRisk = findViewById<TextView>(R.id.textRiskLevel)
        val textMissing = findViewById<TextView>(R.id.textMissingPpe)
        val riskBadge = findViewById<TextView>(R.id.riskBadge)

        textRisk.text = riskLevel.label
        riskBadge.text = riskLevel.label
        riskBadge.setBackgroundResource(riskLevel.badgeRes)

        textMissing.text = if (missingList.isEmpty()) {
            "All PPE worn"
        } else {
            "Missing: ${missingList.joinToString(", ")}" 
        }

        val warningMessage = when (riskLevel) {
            RiskLevel.SAFE -> "Safety check passed. All PPE is worn."
            RiskLevel.MEDIUM -> "Medium risk. Please wear the missing PPE before starting work."
            RiskLevel.HIGH -> "High risk. Stop work now and wear all required PPE."
        }
        pendingMessage = warningMessage
        shouldAutoSpeak = riskLevel != RiskLevel.SAFE

        findViewById<Button>(R.id.btnVoiceAlert).setOnClickListener {
            speakWarning()
        }

        findViewById<Button>(R.id.btnReportIncident).setOnClickListener {
            startActivity(Intent(this, IncidentReportActivity::class.java))
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            textToSpeech?.language = Locale.US
            if (shouldAutoSpeak) {
                speakWarning()
            }
        }
    }

    private fun speakWarning() {
        val message = pendingMessage ?: return
        textToSpeech?.speak(message, TextToSpeech.QUEUE_FLUSH, null, "risk_warning")
    }

    override fun onDestroy() {
        textToSpeech?.shutdown()
        super.onDestroy()
    }
}
