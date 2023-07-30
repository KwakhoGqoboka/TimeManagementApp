package com.example.design

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton

class Catergory_details : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catergory_details)

        val categoriesButton = findViewById<ImageButton>(R.id.imageButton)
        categoriesButton.setOnClickListener {
            val intent = Intent(this, AddCatergory::class.java)
            startActivity(intent)
        }


        val timeButton = findViewById<ImageButton>(R.id.imageButton4)
        timeButton.setOnClickListener {
            val intent = Intent(this, addTimesheet::class.java)
            startActivity(intent)
        }

        val goalsButton = findViewById<ImageButton>(R.id.imageButton5)
        goalsButton.setOnClickListener {
            val intent = Intent(this, ActivityGraph::class.java)
            startActivity(intent)
        }

        val detailDesc: TextView = findViewById(R.id.detailDesc)
        val detailTitle: TextView = findViewById(R.id.detailTitle)
        val detailBudget: TextView = findViewById(R.id.detailDesc)
        val detailImage: ImageView = findViewById(R.id.detailImage)
        //val detaiDesc: TextView = findViewById(R.id.detailDesc)

        val bundle = intent.extras
        bundle?.apply {
            detailDesc?.text = getString("Description")
            detailTitle?.text = getString("Title")

            detailBudget?.text = getString("Budget")
            Glide.with(this@Catergory_details).load(getString("Image")).into(detailImage)





        }
    }

        /*private lateinit var detailTitle: TextView
        private lateinit var detailDesc: TextView
        private lateinit var detailBudget: TextView
        private lateinit var detailLocation: TextView
        private lateinit var detailImage: ImageView

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_catergory_details)

            detailTitle = findViewById(R.id.detailTitle)
            detailDesc = findViewById(R.id.detailDesc)
            detailLocation = findViewById(R.id.detailLocation)
            detailBudget = findViewById(R.id.detailBudget)
            detailImage = findViewById(R.id.detailImage)

            val bundle = intent.extras
            if (bundle != null) {
                detailTitle.text = bundle.getString("Title")
                detailDesc.text = bundle.getString("Description")
                detailLocation.text = bundle.getString("Location")
                detailBudget.text = bundle.getString("Budget")

                Glide.with(this)
                    .load(bundle.getString("Image"))
                    .into(detailImage)
            }

            val floatingActionButton = findViewById<FloatingActionButton>(R.id.floatingActionButton3)
            floatingActionButton.setOnClickListener {
                // Start the desired activity
                val intent = Intent(this, AddCatergory::class.java)
                startActivity(intent)
            }
            val categoriesButton = findViewById<ImageButton>(R.id.imageButton)
            categoriesButton.setOnClickListener {
                val intent = Intent(this, AddCatergory::class.java)
                startActivity(intent)
            }

            val timeButton = findViewById<ImageButton>(R.id.imageButton4)
            timeButton.setOnClickListener {
                val intent = Intent(this, addTimesheet::class.java)
                startActivity(intent)
            }

            val goalsButton = findViewById<ImageButton>(R.id.imageButton5)
            goalsButton.setOnClickListener {
                val intent = Intent(this, TOTAL_HOURS::class.java)
                startActivity(intent)
            }
        }*/
}
