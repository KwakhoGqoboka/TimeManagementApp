package com.example.design
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.design.CategoryEntry
import com.example.design.R

class CategoryAdapter(context: Context, private val entries: List<CategoryEntry>) :
    ArrayAdapter<CategoryEntry>(context, R.layout.list_item_category, entries) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        if (view == null) {
            val inflater = LayoutInflater.from(context)
            view = inflater.inflate(R.layout.list_item_category, parent, false)
        }

        val entry = entries[position]

        val dateTextView = view!!.findViewById<TextView>(R.id.dateTextView)
        val startTimeTextView = view.findViewById<TextView>(R.id.startTimeTextView)
        val endTimeTextView = view.findViewById<TextView>(R.id.endTimeTextView)
        val descriptionTextView = view.findViewById<TextView>(R.id.descriptionTextView)
        val categoryTextView = view.findViewById<TextView>(R.id.categoryTextView)
        val photoImageView = view.findViewById<ImageView>(R.id.photoImageView)

        dateTextView.text = entry.date
        startTimeTextView.text = entry.startTime
        endTimeTextView.text = entry.endTime
        descriptionTextView.text = entry.description
        categoryTextView.text = entry.category

        // Load the photo if available
        if (entry.photoPath != null) {
            Glide.with(context)
                .load(entry.photoPath)
                .into(photoImageView)
        } else {
            // No photo available, clear the ImageView
            photoImageView.setImageBitmap(null)
        }

        return view
    }
}
