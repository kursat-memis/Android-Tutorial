package com.kursatmemis.fragmentlifecycle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.e("mKm - activity", "onCreate")

        val fragmentA = FragmentA()
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()

        transaction.add(R.id.root_element, fragmentA, "fragmentA")

        transaction.commit()

    }

    override fun onStart() {
        super.onStart()
        Log.e("mKm - activity", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.e("mKm - activity", "onResume")
    }

    override fun onRestart() {
        super.onRestart()
        Log.e("mKm - activity", "onRestart")
    }

    override fun onPause() {
        super.onPause()
        Log.e("mKm - activity", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.e("mKm - activity", "onStop")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.e("mKm - activity", "onSaveInstanceState")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.e("mKm - activity", "onRestoreInstanceState")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("mKm - activity", "onDestroy")
    }
}