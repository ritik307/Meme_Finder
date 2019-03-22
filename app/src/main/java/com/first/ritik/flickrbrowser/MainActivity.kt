package com.first.ritik.flickrbrowser

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.net.URI


private const val TAG="Main_Activity" //to make a value static
class MainActivity : BaseActivity(),GetRawData.OnDownloadComplete,GetFlickrJsonData.OnDataAvailable,RecyclerItemClickListener.OnRecyclerClickListener {

    private val flickrRecyclerViewAdapter=FlickrRecyclerViewAdapter(ArrayList())

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG,"onCreate called")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        activeToolbar(false)

        recycler_view.layoutManager=LinearLayoutManager(this)   //TO UNDERSTAND RECYCLERVIEW:=> https://contextneutral.com/story/understanding-recyclerview-part-1-the-basics
        recycler_view.adapter= flickrRecyclerViewAdapter

        recycler_view.addOnItemTouchListener(RecyclerItemClickListener(this,recycler_view,this))

        val url = createURI("https://api.flickr.com/services/feeds/photos_public.gne","android,oreo","en-us",true)
        val getRawData = GetRawData(this)
        getRawData.execute(url)


//        fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
//        }


        Log.d(TAG,"onCreate Ends")
    }

    override fun onItemClick(view: View, position: Int) {
        Log.d(TAG,"onItemClick starts ")
        val photo=flickrRecyclerViewAdapter.getPhoto(position)
        if(photo !=null)
        {
            val intent= Intent(this,PhotoDetailsActivity::class.java)
            intent.putExtra("PHOTO_TRANSFER",photo)
            startActivity(intent)
        }
    }

    override fun onItemLongClick(view: View, position: Int) {
        Log.d(TAG,"onItemLongClick starts")
        Toast.makeText(this,"Long Press at position $position ",Toast.LENGTH_SHORT).show()
    }

    fun createURI(baseURl:String, searchCriteria:String, lang:String, matchAll:Boolean):String
    {
        Log.d(TAG,"createUri starts")
        return Uri.parse(baseURl).buildUpon().
            appendQueryParameter("tags",searchCriteria).
            appendQueryParameter("tagmode",if(matchAll) "All" else "ANY").
            appendQueryParameter("lang",lang).
            appendQueryParameter("format","json").
            appendQueryParameter("nojsoncallback","1").build().toString()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        Log.d(TAG,"onCreateOption menu called")
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        Log.d(TAG,"onOptionItemSelected called")
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
//    companion object {                   //you can use companion object also to make the vale static
//        private val TAG="Main_Activity"
//    }
    override fun onDownloadComplete(data:String,status:DownloadStatus){
        if(status==DownloadStatus.OK)
        {
            Log.d(TAG,"onDownloadComplete called and the data is $data ")
            val getFlickrJsonData=GetFlickrJsonData(this)
            getFlickrJsonData.execute(data)
        }else{
            Log.d(TAG,"onDownloadComplete falied with status = $status and the error is = $data")
        }
    }

    override fun onDataAvailable(data: List<Photo>) {
        Log.d(TAG,"onDataAvailable STARTS")
        flickrRecyclerViewAdapter.loadNewData(data)
        Log.d(TAG,"onDataAvailable ends ")
    }

    override fun onError(E: Exception) {
        Log.d(TAG,"onError called with excetion ${E.message}")
    }
}
