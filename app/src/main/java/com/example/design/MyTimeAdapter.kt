package com.example.design

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MyTimeAdapter (private val context: Context, private var dataList: List<TimesheetDataClass>) :
    RecyclerView.Adapter<MyViewHolder1>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder1 {
        val view: View = LayoutInflater.from(parent.context).inflate(
            R.layout.recyclertimesheet,
            parent,
            false
        )
        return MyViewHolder1(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder1, position: Int) {
        val data: TimesheetDataClass = dataList[position]

        Glide.with(context).load(data.dateImage).into(holder.recImage)
        holder.recDate.text = data.dateLabel
        holder.recStartTime.text = data.dateStartTime.toString()
        holder.recEndTime.text = data.dateEndTime.toString()
        holder.recDescription.text = data.dateDescription
        holder.recCategory.text = data.dateCategory


        holder.recTimesheet.setOnClickListener {
            val intent = Intent(context, TimeSheetDetailActivity::class.java)
            intent.putExtra("Image", dataList[holder.adapterPosition].dateImage)
            intent.putExtra("Date", dataList[holder.adapterPosition].dateLabel)
            intent.putExtra("StartTime", dataList[holder.adapterPosition].dateStartTime)
            intent.putExtra("EndTime", dataList[holder.adapterPosition].dateEndTime)
            intent.putExtra("Description", dataList[holder.adapterPosition].dateDescription)
            intent.putExtra("Category", dataList[holder.adapterPosition].dateCategory)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

}

class MyViewHolder1(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val recImage: ImageView = itemView.findViewById(R.id.recImage)
    val recDate: TextView = itemView.findViewById(R.id.recDate)
    val recStartTime: TextView = itemView.findViewById(R.id.recStartTime)
    val recEndTime: TextView = itemView.findViewById(R.id.recEndTime)
    val recDescription: TextView = itemView.findViewById(R.id.recDescription)
    val recCategory: TextView = itemView.findViewById(R.id.recCategory)
    val recTimesheet: CardView = itemView.findViewById(R.id.recCardTimeSheet)
}