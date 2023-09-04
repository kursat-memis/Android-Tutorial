package com.kursatmemis.android_life_cycle

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog

class DialogActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dialog)
        Log.w("mKm - lifecycle", "onCreate - DialogActivity")
    }

    override fun onStart() {
        super.onStart()
        Log.w("mKm - lifecycle", "onStart - DialogActivity")
    }

    override fun onResume() {
        super.onResume()
        Log.w("mKm - lifecycle", "onResume - DialogActivity")
    }

    override fun onPause() {
        super.onPause()
        Log.w("mKm - lifecycle", "onPause - DialogActivity")
    }

    override fun onStop() {
        super.onStop()
        Log.w("mKm - lifecycle", "onStop - DialogActivity")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.w("mKm - lifecycle", "onDestroy - DialogActivity")
    }

    override fun onRestart() {
        super.onRestart()
        Log.w("mKm - lifecycle", "onRestart - DialogActivity")
    }


}