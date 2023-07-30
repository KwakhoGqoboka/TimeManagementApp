package com.example.design

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*

class SelectingTimesheetEntry : AppCompatActivity() {
    private lateinit var spinner: Spinner
    private lateinit var listView: ListView
    private lateinit var listItems: Array<String>
    private lateinit var listViewAdapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selecting_timesheet_entry)

        spinner = findViewById(R.id.spinner)
        val spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.menu_options, android.R.layout.simple_spinner_item)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = spinnerAdapter

        listView = findViewById(R.id.listView)
        listItems = arrayOf("OPSC") // Replace with your desired list items

        listViewAdapter = ArrayAdapter(this, android.R.layout.list_content)
        listView.adapter = listViewAdapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                if (position != 0) { // Change the condition based on your requirements
                    val selectedItems = arrayOf(listItems[position - 1])
                    listViewAdapter.clear()
                    listViewAdapter.addAll(*selectedItems)
                    listViewAdapter.notifyDataSetChanged()
                    listView.visibility = View.VISIBLE
                } else {
                    listView.visibility = View.GONE
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Do nothing
            }
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


    }
}