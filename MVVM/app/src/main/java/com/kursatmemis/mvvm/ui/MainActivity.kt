package com.kursatmemis.mvvm.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.kursatmemis.mvvm.databinding.ActivityMainBinding
import com.kursatmemis.mvvm.models.Calculator
import com.kursatmemis.mvvm.viewmodels.MainActivityViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var calculator: Calculator

        binding.addButton.setOnClickListener{
            calculator = getCalculator()
            viewModel.add(calculator)
        }

        binding.multiplyButton.setOnClickListener{
            calculator = getCalculator()
            viewModel.multiply(calculator)
        }

        viewModel.calculatorResult.observe(this) {
            binding.resultTextView.text = it.result
        }
    }

    private fun getCalculator(): Calculator {
        val number1 = binding.number1EditText.text.toString()
        val number2 = binding.number2EditText.text.toString()
        return Calculator(number1, number2, "0")
    }

}