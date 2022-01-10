package com.example.quakereport

import android.content.Context
import android.content.Intent
import android.net.*
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import java.util.concurrent.Executors

class EarthquakeActivity : AppCompatActivity() {

    companion object {
        private val LOG_TAG = EarthquakeActivity::class.qualifiedName
    }

    private val executor = Executors.newSingleThreadExecutor()
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.earthquake_activity)

        val isConnected = isInternetAvailable(this)

        if (isConnected) populateList()
        else {
            (findViewById<ProgressBar>(R.id.progressBar)).visibility = View.GONE
            val emptyView: TextView = findViewById(R.id.empty_list_item)
            emptyView.text = getString(R.string.no_internet_connection)
            emptyView.visibility = View.VISIBLE
        }
    }

    private fun populateList() {
        val model: EarthquakeViewModel by viewModels()
        model.getEarthquakes().observe(this, Observer {
            (findViewById<ProgressBar>(R.id.progressBar)).visibility = View.GONE

            // find reference to {@link ListView} in the layout
            val earthquakeListView: ListView = findViewById(R.id.list)

            // Create a new {@link ArrayAdapter} of earthquakes
            val adapter = EarthquakeAdapter(
                this, it
            )

            val emptyView: TextView = findViewById(R.id.empty_list_item)

            earthquakeListView.emptyView = emptyView

            // set adapter on {@link ListView}
            // so list can be populated in UI
            earthquakeListView.adapter = adapter

            earthquakeListView.setOnItemClickListener { _, _, i, _ ->
                val currentEarthquake = adapter.getItem(i)

                val earthquakeUri = Uri.parse(currentEarthquake?.mUrl)

                val websiteIntent = Intent(Intent.ACTION_VIEW, earthquakeUri)

                startActivity(websiteIntent)
            }
        })
    }

    private fun isInternetAvailable(context: Context): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        var isConnected: Boolean = false

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (cm.activeNetwork != null && cm.getNetworkCapabilities(cm.activeNetwork) != null) {
                isConnected = true
            }
        } else {
            val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
            isConnected = activeNetwork?.isConnectedOrConnecting == true
        }

        return isConnected
    }
}