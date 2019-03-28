package com.first.ritik.flickrbrowser

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso

class FlickrImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    var thumbnail: ImageView = view.findViewById(R.id.thumbnail)
    var title: TextView = view.findViewById(R.id.title)
}

class FlickrRecyclerViewAdapter(private var photoList: List<Photo>) : RecyclerView.Adapter<FlickrImageViewHolder>() {

    val TAG = "FlickrRecyclerViewAdapt" //cannot have more than 25 characters in Log Tag

    override fun getItemCount(): Int {
//        Log.d(TAG, ".getItemCount called")
        return if (photoList.isNotEmpty()) photoList.size else 0
    }

    fun loadNewData(newPhoto: List<Photo>) {
        photoList = newPhoto
        notifyDataSetChanged()
    }

    fun getPhoto(position: Int): Photo? {
        return if (photoList.isNotEmpty()) photoList[position] else null
    }



    override fun onBindViewHolder(holder: FlickrImageViewHolder, position: Int) {
        //this method is called by the recyclerview when it want new data to be stored in a view holder
        val photoItem = photoList[position]
        Picasso.get().load(photoItem.photoUrl)
            .error(R.drawable.baseline_terrain_black_48dp)     //show the image when there is error
            .placeholder(R.drawable.baseline_terrain_black_48dp) //show the image when the requested image is not present IT JUST ACT AS A PLACEHOLDER
            .into(holder.thumbnail)

        holder.title.text = photoItem.title
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlickrImageViewHolder {
        // Called by the layout manager when it needs a new view
        Log.d(TAG, ".onCreateViewHolder new view requested")
        val view = LayoutInflater.from(parent.context).inflate(R.layout.browse, parent, false)
        return FlickrImageViewHolder(view)
    }
}