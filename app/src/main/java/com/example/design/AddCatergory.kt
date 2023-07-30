package com.example.design

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*

class AddCatergory : AppCompatActivity() {
    private lateinit var fab: FloatingActionButton
    private lateinit var recyclerView: RecyclerView
    private lateinit var dataList: MutableList<DataClass>
    private lateinit var databaseReference: DatabaseReference
    private lateinit var eventListener: ValueEventListener
    private lateinit var adapter: MyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_catergory)

        fab = findViewById(R.id.floatingActionButton)
        recyclerView = findViewById(R.id.recyclerView)

        val gridLayoutManager = GridLayoutManager(this, 1)
        recyclerView.layoutManager = gridLayoutManager

        dataList = mutableListOf()
        adapter = MyAdapter(this, dataList)
        recyclerView.adapter = adapter

        databaseReference = FirebaseDatabase.getInstance().getReference("Android Tutorials")

        eventListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                dataList.clear()
                for (itemSnapshot in snapshot.children) {
                    val dataClass = itemSnapshot.getValue(DataClass::class.java)
                    dataClass?.let { dataList.add(it) }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle database error
            }
        }

        fab.setOnClickListener {
            val intent = Intent(this, catergories::class.java)
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
            val intent = Intent(this, ActivityGraph::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        databaseReference.addValueEventListener(eventListener)
    }

    override fun onStop() {
        super.onStop()
        databaseReference.removeEventListener(eventListener)
    }


}

/*fun searchList(text: String) {
       val searchList = dataList.filter { dataClass ->
           dataClass.dataName.toLowerCase(Locale.ROOT).contains(text.toLowerCase(Locale.ROOT))
       }
       adapter.searchDataList(searchList)
   }*/


/*listView = findViewById(R.id.catListView)

val categoryEntries =
    intent.getSerializableExtra("category_entry") as? ArrayList<CategoryEntry>

val nonNullCategories = categoryEntries ?: ArrayList<CategoryEntry>()

val adapter = CategoryAdapter(this, nonNullCategories)
listView.adapter = adapter


val floatingActionButton = findViewById<FloatingActionButton>(R.id.floatingActionButton)
floatingActionButton.setOnClickListener {
    // Start the desired activity
    val intent = Intent(this, catergories::class.java)
    startActivity(intent)
}



val categoriesButton = findViewById<ImageButton>(R.id.imageButton)
categoriesButton.setOnClickListener {
    val intent = Intent(this, AddCatergory::class.java)
}        startActivity(intent)
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


