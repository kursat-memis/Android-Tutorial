package com.kursatmemis.mvvm.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kursatmemis.mvvm.models.Calculator

class MainActivityViewModel : ViewModel() {
    var calculatorResult: MutableLiveData<Calculator>

    init {
        val calculator = Calculator("0", "0", "0")
        calculatorResult = MutableLiveData(calculator)
    }

    fun add(calculator: Calculator) {
        val result = (calculator.number1.toInt() + calculator.number2.toInt()).toString()
        calculatorResult.value = Calculator(calculator.number1, calculator.number2, result)
    }

    fun multiply(calculator: Calculator) {
        val result = (calculator.number1.toInt() * calculator.number2.toInt()).toString()
        calculatorResult.value = Calculator(calculator.number1, calculator.number2, result)
    }
}