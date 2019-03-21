package com.first.ritik.flickrbrowser


import android.os.AsyncTask
import android.util.Log
import java.io.IOException
import java.net.MalformedURLException
import java.net.URL



enum class DownloadStatus {
OK,IDLE,NOT_INITIALIZED,FAILED_OR_EMPTY,PERMISSION_ERROR,ERROR
}
class GetRawData(private val listener:MainActivity):AsyncTask<String,Void,String>() {

    private val TAG="GetRawData"
    private var downloadStatus=DownloadStatus.IDLE

    interface OnDownloadComplete {
        fun onDownloadComplete(data:String,status:DownloadStatus)
    }

    override fun onPostExecute(result: String) {
        Log.d(TAG,"onPostexecution called ")
        listener.onDownloadComplete(result, downloadStatus)
    }
    override fun doInBackground(vararg params: String?): String {
        Log.d(TAG,"doInBcakground called ")
        if(params[0]==null) {
            downloadStatus=DownloadStatus.NOT_INITIALIZED
            return "No URL specified"
        }
        try {
            downloadStatus=DownloadStatus.OK
            return URL(params[0]).readText()
        }
        catch (e:Exception){
            val errorMessage=when(e){
                is MalformedURLException ->{
                    downloadStatus=DownloadStatus.NOT_INITIALIZED
                    "doInBaackgorund: Invalid URL ${e.message}"
                }
                is IOException->{
                    downloadStatus=DownloadStatus.FAILED_OR_EMPTY
                    "doInBackground : IOException reading data ${e.message}"
                }
                is SecurityException->{
                    downloadStatus=DownloadStatus.PERMISSION_ERROR
                    "doInBackground : Security Exception ${e.message}"
                }
                else->{
                    downloadStatus=DownloadStatus.ERROR
                    "doInBackground : Unknown Exception ${e.message}"
                }
            }
            Log.e(TAG,errorMessage)
            return errorMessage
        }

    }
}