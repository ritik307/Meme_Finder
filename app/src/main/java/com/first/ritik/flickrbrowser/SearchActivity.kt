package com.first.ritik.flickrbrowser

import android.app.SearchManager
import android.content.ComponentName
import android.content.Context
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.util.Log
import android.view.Menu
import android.widget.SearchView

class SearchActivity : BaseActivity() {
    private val TAG = "SearchActivity"
    private var searchView: SearchView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, ".onCreate: starts")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        activeToolbar(true)
        Log.d(TAG,".onCreate ends")
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        Log.d(TAG, ".onCreateOptionsMenu: starts")
        menuInflater.inflate(R.menu.menu_search, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = menu.findItem(R.id.app_bar_search).actionView as SearchView
        val searchableInfo = searchManager.getSearchableInfo(componentName)
        searchView?.setSearchableInfo(searchableInfo)
        Log.d(TAG, ".onCreateOptionsMenu: $componentName")
        Log.d(TAG, ".onCreateOptionsMenu: hint is ${searchView?.queryHint}")
        Log.d(TAG, ".onCreateOptionsMenu: $searchableInfo")

        searchView?.isIconified = false
        searchView?.setOnQueryTextListener(object:SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.d(TAG,".onQuerryTextSubmit: called")
                val sharedPref=PreferenceManager.getDefaultSharedPreferences(applicationContext)
                sharedPref.edit().putString("FLICKR_QUERY",query).apply()
                searchView?.clearFocus()
                finish()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        searchView?.setOnCloseListener{
            finish()
            false
        }
        Log.d(TAG, ".onCreateOptionsMenu: returning")
        return true
    }
}
