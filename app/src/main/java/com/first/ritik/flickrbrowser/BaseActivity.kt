package com.first.ritik.flickrbrowser

import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.TooltipCompat
import android.util.Log
import android.view.View
import android.widget.Toolbar

open class BaseActivity : AppCompatActivity(){
    private val TAG="BaseActivity"

    internal fun activeToolbar(enableHome:Boolean){     // internal fun is the fun that is only available inside a module
        Log.d(TAG,"activitToolbar starts")

        var toolbar=findViewById<View>(R.id.toolbar) as android.support.v7.widget.Toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(enableHome)
    }
}