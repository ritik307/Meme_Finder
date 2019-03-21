package com.first.ritik.flickrbrowser

import android.content.Context
import android.support.v4.view.GestureDetectorCompat
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View

class RecyclerItemClickListener(context:Context,recyclerView:RecyclerView,private val listener:OnRecyclerClickListener):RecyclerView.SimpleOnItemTouchListener() {
    private val TAG="RecyclerItemClickListen"

    interface OnRecyclerClickListener {
        fun onItemClick(view:View,position:Int)
        fun onItemLongClick(view:View,position:Int)

    }

    private val gestureDetection=GestureDetectorCompat(context,object: GestureDetector.SimpleOnGestureListener(){
        override fun onSingleTapUp(e: MotionEvent): Boolean {
            Log.d(TAG,"onSingleTapUp starts")
            val childView=recyclerView.findChildViewUnder(e.x,e.y) //provide the view on which the user have clicked by check the x and y axis of the touch
            Log.d(TAG,".onSingleTapUp calling listener.onItemClick()")
            listener.onItemClick(childView!!,recyclerView.getChildAdapterPosition(childView))
            return true
        }

        override fun onLongPress(e: MotionEvent) {
            Log.d(TAG,"onLongPress starts")
            val childView=recyclerView.findChildViewUnder(e.x,e.y)
            Log.d(TAG,".onLongPress calling listener.onItemLongClick()")
            listener.onItemLongClick(childView!!,recyclerView.getChildAdapterPosition(childView))
        }
    })

    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        Log.d(TAG,"onInterceptTouchEvent:start $e")
        val result = gestureDetection.onTouchEvent(e)
        return result
    }
}