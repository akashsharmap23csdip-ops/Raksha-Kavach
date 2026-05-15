package com.raksha.kavach

import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.raksha.kavach.data.Incident
import com.raksha.kavach.data.IncidentRepository
import com.raksha.kavach.data.ScoreRepository
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class IncidentReportActivity : AppCompatActivity() {
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_incident_report)

        val inputTitle = findViewById<EditText>(R.id.inputIncidentTitle)
        val inputDescription = findViewById<EditText>(R.id.inputIncidentDescription)
        val textDate = findViewById<TextView>(R.id.textIncidentDate)
        val imagePreview = findViewById<ImageView>(R.id.imagePreview)
        val btnPickImage = findViewById<Button>(R.id.btnPickImage)
        val btnSubmit = findViewById<Button>(R.id.btnSubmitIncident)

        val dateString = SimpleDateFormat("yyyy-MM-dd", Locale.US).format(Date())
        textDate.text = dateString

        val picker = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            imageUri = uri
            if (uri != null) {
                imagePreview.setImageURI(uri)
            }
        }

        btnPickImage.setOnClickListener { picker.launch("image/*") }

        btnSubmit.setOnClickListener {
            val title = inputTitle.text.toString().trim()
            val description = inputDescription.text.toString().trim()

            if (title.isEmpty() || description.isEmpty()) {
                Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val repository = IncidentRepository(this)
            val saved = repository.saveIncident(
                Incident(
                    title = title,
                    description = description,
                    date = dateString,
                    imageUri = imageUri?.toString()
                )
            )
            if (saved) {
                ScoreRepository(this).updateScore(-15)
                Toast.makeText(this, "Incident saved", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Save failed", Toast.LENGTH_LONG).show()
            }
        }
    }
}
