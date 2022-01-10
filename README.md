# QuakeReport
An Android app which lists the most recent 10 earthquakes around the world based on data from the USGS.

## About

This app was made following a free Udacity course provided by Google [here](https://www.udacity.com/course/android-basics-networking--ud843). However, it is an updated (as best as I could) version of the app because the tutorial is quite old and includes some deprecated code. The following is a general list of changes I made:
- Updated the project (especially the `gradle.build` files) to make it work with the latest Android Studio version at the time of writing (Arctic Fox 2020.3.1 Patch 4)
- Used Kotlin instead of Java
- Used coroutines instead of `AsyncTask` (which is deprecated)
- Used `ViewModel` and `LiveData` instead of `Loader` (which is deprecated)
- Added network connectivity checks using `getNetworkCapabilities` for compatibility with Android APK 28+ (`networkInfo` is deprecated but still there for backward compatibility)

## Why

I made this as part of my training to become an Android engineer at work.
