package com.example.threelinetest

import android.os.AsyncTask
import android.util.Log
import java.io.IOException
import java.net.MalformedURLException
import java.net.URL

enum class DownloadStatus {
    OK, IDLED, NOT_INITIALISED, FAILED_OR_EMPTY, PERMISSION_ERROR, ERROR
}

interface OnDownloadComplete {
    fun onDownloadComplete(data : String, status : DownloadStatus)
}

class DownloadRawData(private var listener: OnDownloadComplete) : AsyncTask<String, Void, String>() {

    private val TAG = "DownloadRawData"
    private var downloadStatus = DownloadStatus.IDLED

    override fun doInBackground(vararg param: String?): String {
        if  (param[0] == null) {
            downloadStatus = DownloadStatus.NOT_INITIALISED
            return "URL not specified"
        }

        try {
            downloadStatus = DownloadStatus.OK
            return URL(param[0]).readText()
        } catch (e : Exception) {
            val errorMessage = when(e) {
                is MalformedURLException -> {
                    downloadStatus = DownloadStatus.NOT_INITIALISED
                    "doInBackground : Invalid URL ${e.message}"
                }
                is IOException -> {
                    downloadStatus = DownloadStatus.FAILED_OR_EMPTY
                    "doInBackground : IO Exception data not read ${e.message}"
                }
                is SecurityException -> {
                    downloadStatus = DownloadStatus.PERMISSION_ERROR
                    "doInBackground : Security exception needs permission? ${e.message}"
                }
                else -> {
                    downloadStatus = DownloadStatus.ERROR
                    "Unknown error : ${e.message}"
                }
            }

            Log.d(TAG, errorMessage)
            return errorMessage
        }

    }

    override fun onPostExecute(result: String) {
        Log.d(TAG, "onPostExecute called : $result")
        listener.onDownloadComplete(result, downloadStatus)
    }
}