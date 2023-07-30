package com.example.design

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*

class addTimesheet : AppCompatActivity() {
    lateinit var fab: FloatingActionButton
    var recyclerViewTimesheet: RecyclerView? = null
    var dataList: List<TimesheetDataClass>? = null
    var databaseReference: DatabaseReference? = null
    var eventListener: ValueEventListener? = null
    var adapter: MyTimeAdapter? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_timesheet)

        fab = findViewById(R.id.floatingActionButton)
        recyclerViewTimesheet = findViewById<RecyclerView>(R.id.recyclerViewTimesheet)

        val gridLayoutManager = GridLayoutManager(this, 1)
        recyclerViewTimesheet!!.layoutManager = gridLayoutManager
        val builder = AlertDialog.Builder(this)
        builder.setCancelable(false)
        builder.setView(R.layout.progress_layout)
        val dialog = builder.create()
        dialog.show()

        dataList = ArrayList()

        adapter = MyTimeAdapter(this, dataList as ArrayList<TimesheetDataClass>)
        recyclerViewTimesheet!!.adapter = adapter
        databaseReference = FirebaseDatabase.getInstance().getReference("Android Tutorials")
        dialog.show()

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

        eventListener = databaseReference!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                (dataList as ArrayList<TimesheetDataClass>).clear()
                for (itemSnapshot in snapshot.children) {
                    val dataClass = itemSnapshot.getValue(TimesheetDataClass::class.java)
                    dataClass?.let { (dataList as ArrayList<TimesheetDataClass>).add(it) }
                }
                adapter!!.notifyDataSetChanged()
                dialog.dismiss()
            }

            override fun onCancelled(error: DatabaseError) {
                dialog.dismiss()
            }
        })

        fab.setOnClickListener { view ->
            val intent = Intent(this@addTimesheet, timesheetEntries::class.java)
            startActivity(intent)
        }
    }

}



/*   private lateinit var listView: ListView

override fun onCreate(savedInstanceState: Bundle?) {
   super.onCreate(savedInstanceState)
   setContentView(R.layout.activity_add_timesheet)

   listView = findViewById(R.id.timesheetListView)

   val TimesheetEntries = intent.getSerializableExtra("timesheet_entries") as? ArrayList<TimeSheetEntry>

   val nonNullTimesheetEntries = TimesheetEntries ?: ArrayList<TimeSheetEntry>()

   val adapter = TimeSheetEntryAdapter(this, nonNullTimesheetEntries)
   listView.adapter = adapter

   val floatingActionButton = findViewById<FloatingActionButton>(R.id.floatingActionButton)
   floatingActionButton.setOnClickListener {
       // Start the desired activity
       val intent = Intent(this, timesheetEntries::class.java)
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
       val intent = Intent(this, SelectingTimesheetEntry::class.java)
       startActivity(intent)
   }
}
} */
