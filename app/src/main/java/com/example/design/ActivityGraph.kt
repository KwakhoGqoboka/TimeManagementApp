package com.example.design

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate

class ActivityGraph : AppCompatActivity() {

    lateinit var barChart: BarChart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_graph)

        barChart = findViewById(R.id.bar_chart)

        val barEntries = ArrayList<BarEntry>()

        for (i in 1 until 3) {
            val value = (i * 3.0).toFloat()
            val barEntry = BarEntry(i.toFloat(), value)
            barEntries.add(barEntry)
        }

        val goalsButton = findViewById<ImageButton>(R.id.imageButton5)
        goalsButton.setOnClickListener {
            val intent = Intent(this, game::class.java)
            startActivity(intent)
        }
        val categoriesButton = findViewById<ImageButton>(R.id.imageButton)
        categoriesButton.setOnClickListener {
            val intent = Intent(this, AddCatergory::class.java)
            startActivity(intent)
        }

        val barDataSet = BarDataSet(barEntries, "Employees")
        barDataSet.colors = ColorTemplate.COLORFUL_COLORS.toList()
        barDataSet.setDrawValues(false)
        val barData = BarData(barDataSet)

        // Configure XAxis
        val xAxis = barChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.isEnabled = true // Enable the XAxis
        val xAxisLabels = listOf("", "") // Replace with your own labels
        xAxis.valueFormatter = object : IndexAxisValueFormatter(xAxisLabels) {
            override fun getFormattedValue(value: Float): String {
                return super.getFormattedValue(value)
            }
        }
        xAxis.setDrawGridLines(false)
        xAxis.textColor = Color.WHITE // Set label text color to white
        xAxis.labelCount = barEntries.size // Set the number of labels to match the number of bars
        xAxis.axisMinimum = 0.5f // Offset the labels to center with bars
        xAxis.axisMaximum = barEntries.size + 0.5f // Offset the labels to center with bars

        // Configure YAxis
        val leftYAxis = barChart.axisLeft
        leftYAxis.setDrawGridLines(false)
        leftYAxis.axisMinimum = 0f
        leftYAxis.textColor = Color.WHITE // Set label text color to white

        val rightYAxis = barChart.axisRight
        rightYAxis.isEnabled = false

        // Set data and other chart configurations
        barChart.data = barData
        barChart.animateY(5000)
        barChart.description.text = ""
        barChart.description.textColor = Color.BLUE
        barChart.invalidate()
    }
}
