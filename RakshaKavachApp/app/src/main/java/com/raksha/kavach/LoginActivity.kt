package com.raksha.kavach

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.raksha.kavach.data.AuthRepository

class LoginActivity : AppCompatActivity() {
    private lateinit var inputEmail: EditText
    private lateinit var inputPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnRegister: Button
    private lateinit var btnDemo: Button
    private lateinit var authRepository: AuthRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        inputEmail = findViewById(R.id.inputEmail)
        inputPassword = findViewById(R.id.inputPassword)
        btnLogin = findViewById(R.id.btnLogin)
        btnRegister = findViewById(R.id.btnRegister)
        btnDemo = findViewById(R.id.btnDemo)
        authRepository = AuthRepository(this)

        btnLogin.setOnClickListener { handleLogin() }
        btnRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
        btnDemo.setOnClickListener {
            authRepository.startDemoSession()
            startActivity(Intent(this, DashboardActivity::class.java))
        }
    }

    override fun onStart() {
        super.onStart()
        if (authRepository.isLoggedIn()) {
            startActivity(Intent(this, DashboardActivity::class.java))
            finish()
        }
    }

    private fun handleLogin() {
        val email = inputEmail.text.toString().trim()
        val password = inputPassword.text.toString().trim()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Enter email and password", Toast.LENGTH_SHORT).show()
            return
        }

        val (ok, message) = authRepository.login(email, password)
        if (ok) {
            startActivity(Intent(this, DashboardActivity::class.java))
            finish()
        } else {
            Toast.makeText(this, message ?: "Login failed", Toast.LENGTH_LONG).show()
        }
    }
}
