package com.kursatmemis.mvvm.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kursatmemis.mvvm.model.Calculator

class MainActivityViewModel : ViewModel() {
    var calculator: MutableLiveData<Calculator>

    init {
        val calculator = Calculator("0", "0", "0")
        this.calculator = MutableLiveData(calculator)
    }

    fun add(calculator: Calculator) {
        val result = (calculator.number1.toInt() + calculator.number2.toInt()).toString()
        this.calculator.value = Calculator(calculator.number1, calculator.number2, result)
    }

    fun multiply(calculator: Calculator) {
        val result = (calculator.number1.toInt() * calculator.number2.toInt()).toString()
        this.calculator.value = Calculator(calculator.number1, calculator.number2, result)
    }

    override fun onCleared() {
        super.onCleared()
        Log.w("mKm - viewmodel", "onCleared()")
    }

}