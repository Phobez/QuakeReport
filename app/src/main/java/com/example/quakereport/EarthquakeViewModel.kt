package com.example.quakereport

import android.app.Application
import androidx.lifecycle.*
import androidx.preference.PreferenceManager
import kotlinx.coroutines.launch

class EarthquakeViewModel(application: Application) : AndroidViewModel(application) {
    private val earthquakes: MutableLiveData<List<Earthquake>> by lazy {
        MutableLiveData<List<Earthquake>>()
    }

    private lateinit var minMagnitude: String
    private lateinit var orderBy: String

    init {
        loadEarthquakes()
    }

    fun getEarthquakes(): LiveData<List<Earthquake>> {
        return earthquakes
    }

    private fun loadEarthquakes() {
        val context = getApplication<Application>()

        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)

        minMagnitude = sharedPrefs.getString(
            context.getString(R.string.settings_min_magnitude_key),
            context.getString(R.string.settings_min_magnitude_default)
        ).toString()

        orderBy = sharedPrefs.getString(
            context.getString(R.string.settings_order_by_key),
            context.getString(R.string.settings_order_by_default)
        ).toString()

        viewModelScope.launch {
            val result = QueryUtils.extractEarthquakes(minMagnitude, orderBy)

            earthquakes.postValue(result)
        }
    }
}