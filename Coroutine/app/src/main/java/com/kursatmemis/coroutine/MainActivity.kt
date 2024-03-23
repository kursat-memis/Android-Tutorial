package com.kursatmemis.coroutine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    override fun onDestroy() {
        super.onDestroy()
        log("mKm - scopes", "onDestroy")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView = findViewById<TextView>(R.id.textView)

        dispatchers()
    }

    private fun dispatchers() {
        val tag = "mKm - dispatchers"
        GlobalScope.launch(Dispatchers.Main) {
            val message = "Thread - Dispatchers.Main: ${Thread.currentThread().name}"
            log(tag, message)
        }

        GlobalScope.launch(Dispatchers.IO) {
            val message = "Thread - Dispatchers.IO: ${Thread.currentThread().name}"
            log(tag, message)
        }

        GlobalScope.launch(Dispatchers.Default) {
            val message = "Thread - Dispatchers.Default: ${Thread.currentThread().name}"
            log(tag, message)
        }

        GlobalScope.launch(Dispatchers.Unconfined) {
            val message = "Thread - Dispatchers.Unconfined: ${Thread.currentThread().name}"
            log(tag, message)
        }
    }

    fun onClick(view: View) {
        val randomNumber = Random.nextInt(0, 100)
        val textView = findViewById<TextView>(R.id.textView)
        textView.text = randomNumber.toString()
    }

    private fun log(tag: String = "mKm - krst", message: String) {
        Log.w(tag, message)
    }

}


data class User(val name: String)