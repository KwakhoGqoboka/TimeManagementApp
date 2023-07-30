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

class MyAdapter(private val context: Context, private var dataList: List<DataClass>) :
    RecyclerView.Adapter<myViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(
            R.layout.recyclercategory,
            parent,
            false
        )
        return myViewHolder(view)
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        val data: DataClass = dataList[position]

        holder.recTitle.text = data.dataName
        holder.recDesc.text = data.dataDesc
        holder.recBud.text = data.dataBudget.toString()
        holder.recMin.text = data.dataMin.toString()
        holder.recMax.text = data.dataMax.toString()
        Glide.with(context).load(data.dataImage).into(holder.recImage)
        //holder.recLoc.text = data.dataBudget

        holder.recCard.setOnClickListener {
            val intent = Intent(context, Catergory_details::class.java)
            intent.putExtra("Description", dataList[holder.adapterPosition].dataDesc)
            intent.putExtra("Title", dataList[holder.adapterPosition].dataName)
            intent.putExtra("Budget", dataList[holder.adapterPosition].dataBudget)
            intent.putExtra("Minimum Hours", dataList[holder.adapterPosition].dataMin)
            intent.putExtra("Maximum Hours", dataList[holder.adapterPosition].dataMax)
            intent.putExtra("Image", dataList[holder.adapterPosition].dataImage)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun searchDataList(searchList: ArrayList<DataClass>) {
        dataList = searchList
        notifyDataSetChanged()
    }

}
class myViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val recTitle: TextView = itemView.findViewById(R.id.recTitle)
    val recDesc: TextView = itemView.findViewById(R.id.recDesc)
    val recBud: TextView = itemView.findViewById(R.id.recBud)
    val recMin: TextView = itemView.findViewById(R.id.recMin)
    val recMax: TextView = itemView.findViewById(R.id.recMax)
    val recCard: CardView = itemView.findViewById(R.id.recCard)
    val recImage: ImageView = itemView.findViewById(R.id.recImage)

}