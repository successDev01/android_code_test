package com.example.threelinetest

import android.os.AsyncTask
import android.util.Log
import org.json.JSONObject
import java.lang.Exception

class GetWeatherJsonData(private val listener : OnDataAvailable) : AsyncTask<String, Void, ArrayList<Weather>>() {

    private val TAG  = "GetWeatherJsonData"

    override fun doInBackground(vararg param: String?): ArrayList<Weather> {
        Log.d(TAG, "doInBackground called $param")

        val weatherArrayList = ArrayList<Weather>()

        try {
            val jsonData = JSONObject(param[0])
            val itemArray = jsonData.getJSONArray("list")

            for (i in 0 until itemArray.length()) {
                val item = itemArray.getJSONObject(i)
                val main = itemArray.getJSONObject(i).getJSONObject("main")
                val weatherVar = item.getJSONArray("weather")
                val description = weatherVar.getJSONObject(0).getString("description")
                val cloud = weatherVar.getJSONObject(0).getString("main")
                val humidity = main.getInt("humidity")

                val weather = Weather(cloud, description, humidity)
                weatherArrayList.add(weather)
                Log.d(TAG, "Weather : $weather")
            }
        } catch (e : Exception) {
            e.printStackTrace()
            Log.d(TAG, "Unable to parse response : ${e.message}")
            // cancel(true)
            listener.onError(e)
        }

        return  weatherArrayList
    }

    override fun onPostExecute(result: ArrayList<Weather>) {
        Log.d(TAG, "onPostExecute : $result")
        listener.onDataAvailable(result)
        Log.d(TAG, "onPostExecute end")
    }

    interface OnDataAvailable {
        fun onDataAvailable(data : List<Weather>)
        fun onError(exception : Exception)
    }
}