package com.kursatmemis.android_tutorial

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.Toast
import com.shashank.sony.fancytoastlib.FancyToast

class MainActivity : AppCompatActivity() {

    private lateinit var showDefaultToastButton: Button
    private lateinit var showCustomToastButton: Button
    private lateinit var showFromLibraryToast: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        showDefaultToastButton = findViewById(R.id.showDefaultToastButton)
        showCustomToastButton = findViewById(R.id.showCustomToastButton)
        showFromLibraryToast = findViewById(R.id.showFromLibraryToast)

        showDefaultToastButton.setOnClickListener {
            Toast.makeText(this@MainActivity, "Toast Message", Toast.LENGTH_SHORT).show()
        }

        showCustomToastButton.setOnClickListener {
            val layout = layoutInflater.inflate(R.layout.custom_toast, null)
            val myToast = Toast(this@MainActivity)
            myToast.duration = Toast.LENGTH_SHORT
            myToast.setGravity(Gravity.TOP or Gravity.START, 150, 100)
            myToast.view = layout
            myToast.show()
        }

        // Fancy libary'den Toast Kullanildi.
        showFromLibraryToast.setOnClickListener {
            FancyToast.makeText(
                this,
                "Toast From Fancy Library !",
                FancyToast.LENGTH_SHORT,
                FancyToast.SUCCESS,
                true
            ).show()
        }

    }
}