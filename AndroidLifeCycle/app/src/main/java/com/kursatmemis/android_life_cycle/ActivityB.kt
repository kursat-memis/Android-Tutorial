package com.kursatmemis.android_life_cycle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class ActivityB : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_b)

        Log.w("mKm - lifecycle", "onCreate - Activity-2")
    }

    override fun onStart() {
        super.onStart()
        Log.w("mKm - lifecycle", "onStart - Activity-2")
    }

    override fun onResume() {
        super.onResume()
        Log.w("mKm - lifecycle", "onResume - Activity-2")
    }

    override fun onPause() {
        super.onPause()
        Log.w("mKm - lifecycle", "onPause - Activity-2")
    }

    override fun onStop() {
        super.onStop()
        Log.w("mKm - lifecycle", "onStop - Activity-2")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.w("mKm - lifecycle", "onDestroy - Activity-2")
    }

    override fun onRestart() {
        super.onRestart()
        Log.w("mKm - lifecycle", "onRestart - Activity-2")
    }

}