package com.example.threelinetest.ui.weekly

import com.example.threelinetest.Weather

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.threelinetest.R

class WeeklyWeatherViewHolder(view : View) : RecyclerView.ViewHolder(view) {
    private val TAG = "WeeklyWeatherViewHolder"

    val thumbnail : ImageView = view.findViewById(R.id.thumbnail)
    val week_day_cloud: TextView = view.findViewById(R.id.week_day_cloud)
    val week_day_desc : TextView = view.findViewById(R.id.week_day_cloud_description)
}

class WeeklyWeatherAdapter() : RecyclerView.Adapter<WeeklyWeatherViewHolder>() {
    private val TAG = "WeeklyWeatherAdapter"
    private var weatherList : List<Weather> = ArrayList<Weather>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeeklyWeatherViewHolder {
        Log.d(TAG, "onCreateViewHolder called")
        val view = LayoutInflater.from(parent.context).inflate(R.layout.weekly_list_browse, parent, false)
        return WeeklyWeatherViewHolder(view)
    }

    override fun onBindViewHolder(holder: WeeklyWeatherViewHolder, position: Int) {
        if (weatherList.isEmpty()) {
            holder.thumbnail.setImageResource(R.drawable.full_rain)
            holder.week_day_cloud.text = "Snows"
            holder.week_day_desc.text = "No Description"

        } else {
            val weatherListItem = weatherList[position]

            if (weatherListItem.humidity == 100) {
                holder.thumbnail.setImageResource(R.drawable.full_rain)
            } else {
                holder.thumbnail.setImageResource(R.drawable.rain_sun)
            }
            holder.week_day_cloud.text = weatherListItem.cloud
            holder.week_day_desc.text = weatherListItem.description
        }

    }

    override fun getItemCount(): Int {
        return if (weatherList.isNotEmpty()) weatherList.size else 1 // I returned 1 here for it to display No Weather

    }

    fun setWeatherList(newWeatherList : List<Weather>) {
        weatherList = newWeatherList
        notifyDataSetChanged()
    }

}