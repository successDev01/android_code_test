package com.example.threelinetest.ui.weekly

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.threelinetest.*
import com.example.threelinetest.databinding.FragmentWeeklyBinding
import java.lang.Exception

class WeeklyFragment : Fragment(), OnDownloadComplete, GetWeatherJsonData.OnDataAvailable {
    private val TAG = "WeeklyFragment"
    private var _binding: FragmentWeeklyBinding? = null
    private val weeklyWeatherAdapter = WeeklyWeatherAdapter()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(WeeklyViewModel::class.java)

        _binding = FragmentWeeklyBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val recyView : RecyclerView= binding.recyclerView
        recyView?.layoutManager = LinearLayoutManager(context)
        recyView?.adapter = weeklyWeatherAdapter

//        val textView: TextView = binding.textDashboard
//        dashboardViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        return root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val downloadRawData = DownloadRawData(this)
        downloadRawData.execute("https://api.openweathermap.org/data/2.5/forecast?id=524901&appid=d15109fc7df7a2036a4f371bac72e7f0")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDownloadComplete(data: String, status: DownloadStatus) {
        if (status == DownloadStatus.OK) {
            Log.d(TAG, "onDownloadComplete with the data : $data")
            val getWeatherJsonDataJsonData = GetWeatherJsonData(this)
            getWeatherJsonDataJsonData.execute(data)

        } else {
            Log.d(TAG, "onDownloadComplete fails with : $status")
        }
    }

    override fun onDataAvailable(data: List<Weather>) {
        Log.d(TAG, "onDataAvailable  : $data")
        weeklyWeatherAdapter.setWeatherList(data)
    }

    override fun onError(exception: Exception) {
        Log.d(TAG, "onError called")
    }
}