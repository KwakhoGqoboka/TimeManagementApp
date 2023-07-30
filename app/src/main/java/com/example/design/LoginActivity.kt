package com.example.design

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var loginButton: Button
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Initialize Firebase Authentication instance
        auth = FirebaseAuth.getInstance()

        // Find the login button
        loginButton = findViewById(R.id.btn_login)

        // Set an OnClickListener for the login button
        loginButton.setOnClickListener {
            // Perform login logic here
            loginUser()
        }

        val textViewSignup = findViewById<TextView>(R.id.textViewSignup)

        // Set click listener for textViewSignup
        textViewSignup.setOnClickListener {
            // Start RegisterActivity
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loginUser() {
        // Retrieve the input values from the EditText fields
        val email = findViewById<EditText>(R.id.LogUser).text.toString()
        val password = findViewById<EditText>(R.id.LogPass).text.toString()

        // Perform input validation if needed

        // Sign in the user with email and password using Firebase Authentication
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Login success
                    Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()
                    // Proceed to the desired activity or perform additional logic

                    // Start AddCategoryActivity
                    val intent = Intent(this, AddCatergory::class.java)
                    startActivity(intent)
                    finish() // Optional: Finish LoginActivity to prevent going back to it with back button
                } else {
                    // Login failed
                    Toast.makeText(this, "Login failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

}
