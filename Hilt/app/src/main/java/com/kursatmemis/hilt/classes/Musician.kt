package com.kursatmemis.hilt.classes

import android.util.Log
import dagger.hilt.android.scopes.ActivityRetainedScoped
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

// Constructor injection
@ActivityRetainedScoped
class Musician @Inject constructor(private var  instrument: Instrument, private var band: Band) {

    private var dependency: Instrument? = null

    fun sing() {
        Log.w("mKm - hilt", "Musician is singing.")
    }

    // Method injection
    @Inject
    fun setDependency(dependency: Instrument) {
        this.dependency = dependency
        Log.w("mKm - hilt", "Method Injection")
    }

}