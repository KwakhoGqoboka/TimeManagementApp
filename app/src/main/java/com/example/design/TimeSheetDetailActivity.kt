package com.example.design

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class TimeSheetDetailActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_item_timesheet_entry)

        val detailDate: TextView = findViewById(R.id.detailDate)
        val detailStartTime: TextView = findViewById(R.id.detailStartTime)
        val detailEndTime: TextView = findViewById(R.id.detailEndTime)
        val detailDescription: TextView = findViewById(R.id.detailDescription)
        val detailCategory: TextView = findViewById(R.id.detailCategory)
        val detailImage: ImageView = findViewById(R.id.detailImage)

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

        val bundle = intent.extras
        bundle?.apply {
            detailDate?.text = getString("Date")
            detailStartTime?.text = getString("StartTime")
            detailEndTime?.text = getString("EndTime")
            detailDescription?.text = getString("Description")
            detailCategory?.text = getString("Category")
            Glide.with(this@TimeSheetDetailActivity).load(getString("Image")).into(detailImage)
        }

    }

}