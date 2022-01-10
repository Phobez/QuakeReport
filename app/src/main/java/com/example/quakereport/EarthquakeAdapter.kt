package com.example.quakereport

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import kotlin.math.floor

class EarthquakeAdapter(context: Context, earthquakes: List<Earthquake>) :
    ArrayAdapter<Earthquake>(context, 0, earthquakes) {

    @SuppressLint("QueryPermissionsNeeded")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var listItemView = convertView
        if (listItemView == null) {
            listItemView = LayoutInflater.from(context).inflate(
                R.layout.earthquake_list_item, parent, false
            )
        }

        val currentEarthquake = getItem(position)

        val magnitudeView: TextView = listItemView?.findViewById(R.id.magnitude) as TextView
        magnitudeView.text = formatMagnitude(currentEarthquake?.mMagnitude as Double)

        val magnitudeCircle: GradientDrawable = magnitudeView.background as GradientDrawable

        val magnitudeColour = getMagnitudeColour(currentEarthquake.mMagnitude)

        magnitudeCircle.setColor(magnitudeColour)

        val baseLocation: String = currentEarthquake.mLocation
        var splitIndex = baseLocation.indexOf(" of ")

        var locationOffset = ""
        var primaryLocation = ""

        if (splitIndex == -1) {
            locationOffset = context.getString(R.string.near)
            primaryLocation = currentEarthquake.mLocation
        } else {
            splitIndex += 4
            locationOffset = baseLocation.substring(0, splitIndex)
            primaryLocation = baseLocation.substring(splitIndex)
        }

        val locationOffsetView: TextView =
            listItemView.findViewById(R.id.location_offset) as TextView
        locationOffsetView.text = locationOffset

        val primaryLocationView: TextView =
            listItemView.findViewById(R.id.primary_location) as TextView
        primaryLocationView.text = primaryLocation

        val dateView: TextView = listItemView.findViewById(R.id.date) as TextView
        val formattedDate = formatDate(currentEarthquake.mDate)
        dateView.text = formattedDate

        val timeView: TextView = listItemView.findViewById(R.id.time) as TextView
        val formattedTime = formatTime(currentEarthquake.mDate)
        timeView.text = formattedTime

        return listItemView
    }

    @SuppressLint("SimpleDateFormat")
    private fun formatDate(date: Long): String {
        val dateFormat = SimpleDateFormat("LLL dd, yyyy")
        return dateFormat.format(date)
    }

    @SuppressLint("SimpleDateFormat")
    private fun formatTime(date: Long): String {
        val timeFormat = SimpleDateFormat("h:mm a")
        return timeFormat.format(date)
    }

    private fun formatMagnitude(magnitude: Double): String {
        val magnitudeFormat = DecimalFormat("0.0")
        return magnitudeFormat.format(magnitude)
    }

    private fun getMagnitudeColour(magnitude: Double): Int {
        var magnitudeColourResourceId = -1
        magnitudeColourResourceId = when (floor(magnitude).toInt()) {
            0, 1 -> R.color.magnitude1
            2 -> R.color.magnitude2
            3 -> R.color.magnitude3
            4 -> R.color.magnitude4
            5 -> R.color.magnitude5
            6 -> R.color.magnitude6
            7 -> R.color.magnitude7
            8 -> R.color.magnitude8
            9 -> R.color.magnitude9
            else -> R.color.magnitude10plus
        }
        return ContextCompat.getColor(context, magnitudeColourResourceId)
    }
}