package com.example.quakereport

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class EarthquakeViewModel : ViewModel() {
    private val earthquakes: MutableLiveData<List<Earthquake>> by lazy {
        MutableLiveData<List<Earthquake>>()
    }

    init {
        loadEarthquakes()
    }

    fun getEarthquakes(): LiveData<List<Earthquake>> {
        return earthquakes
    }

    private fun loadEarthquakes() {
        Log.d("EarthquakeViewModel", "loadEarthquakes launching.")
        viewModelScope.launch {
            val result = QueryUtils.extractEarthquakes()

            earthquakes.postValue(result)
        }
    }
}