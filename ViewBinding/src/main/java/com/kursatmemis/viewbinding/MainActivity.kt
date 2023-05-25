package com.kursatmemis.viewbinding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kursatmemis.viewbinding.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.increaseButton.setOnClickListener {
            binding.counterTextView.text = "Counter: ${++count}"
        }


    }
}

