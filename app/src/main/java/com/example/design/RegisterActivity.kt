package com.example.design

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {

    private lateinit var registerButton: Button
    private lateinit var alreadyTextView: TextView
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Initialize Firebase Authentication instance
        auth = FirebaseAuth.getInstance()

        // Find the register button
        registerButton = findViewById(R.id.btnReg)

        // Find the already have an account TextView
        alreadyTextView = findViewById(R.id.Already)

        // Set an OnClickListener for the register button
        registerButton.setOnClickListener {
            // Perform registration logic here
            registerUser()
        }

        // Set an OnClickListener for the already have an account TextView
        alreadyTextView.setOnClickListener {
            // Start LoginActivity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun registerUser() {
        // Retrieve the input values from the EditText fields
        val username = findViewById<EditText>(R.id.LogUser).text.toString()
        val email = findViewById<EditText>(R.id.LogPass).text.toString()
        val password = findViewById<EditText>(R.id.inputpassword).text.toString()
        val confirmPassword = findViewById<EditText>(R.id.inputPassCon).text.toString()

        // Perform input validation and registration logic
        // ...

        // Example: Display a toast message to indicate successful registration
        Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show()
    }
}
