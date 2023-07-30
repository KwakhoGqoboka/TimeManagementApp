package com.example.design

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ProgressBar
import kotlinx.coroutines.delay

class LoadingScreen : AppCompatActivity() {

    private lateinit var progressBar: ProgressBar
    private var progressStatus = 0
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading_screen)
        progressBar = findViewById(R.id.progressBar)

        // Start the progress bar update in a background thread
        Thread {
            while (progressStatus < 100) {
                progressStatus += 1

                // Update the progress bar on the main thread
                handler.post {
                    progressBar.progress = progressStatus
                }

                // Delay for a while to simulate a time-consuming task
                try {
                    Thread.sleep(30)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }

            // Open a new activity when the progress bar reaches its maximum value
            val intent = Intent(this@LoadingScreen, LoginActivity::class.java)
            startActivity(intent)

            // Finish the current activity
            finish()
        }.start()
    }
}
