package com.kursatmemis.mvvm.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.kursatmemis.mvvm.databinding.ActivityMainBinding
import com.kursatmemis.mvvm.model.Calculator
import com.kursatmemis.mvvm.viewmodels.MainActivityViewModel

// Not: Repo katmanıda eklenerek MVVM mimarisi daha modüler hale getirilebilir.

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainActivityViewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // owner parametresi: ViewModel'ın bağlı olduğu lifecycle'a sahip yapının referansını ister.
        // ViewModel, burada gönderilen referansa sahip activity'nin varlığına göre bellekte
        // varlığını sürdürecektir.
        mainActivityViewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        /**
         * Not: Aşağıdaki gibi x adında bir viewModel objesi daha tanımladığımız zaman,
         * mainActivityViewModel ve x için ayrı ayrı iki obje oluşmaz. Tek bir obje oluşur ve
         * hem x hem de mainActivityViewModel aynı objeyi temsil eder. Yani x üzerinden yapılan
         * değişiklikler mainActivityViewModel'ıda etkiler.
         */
        // val x = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        var calculator: Calculator

        binding.addButton.setOnClickListener{
            calculator = getCalculator()
            mainActivityViewModel.add(calculator)
        }

        binding.multiplyButton.setOnClickListener{
            calculator = getCalculator()
            mainActivityViewModel.multiply(calculator)
        }

        /**
         * Not: Android, Fragment içinde observe() methodunun 'owner' parametresine 'this' yerine
         * 'viewLifeCycleOwner' ı argüman olarak göndermeyi tavsiye ediyor.
         */
        mainActivityViewModel.calculator.observe(this) {
            binding.resultTextView.text = it.result
        }

    }

    private fun getCalculator(): Calculator {
        val number1 = binding.number1EditText.text.toString()
        val number2 = binding.number2EditText.text.toString()
        return Calculator(number1, number2, "0")
    }

}
