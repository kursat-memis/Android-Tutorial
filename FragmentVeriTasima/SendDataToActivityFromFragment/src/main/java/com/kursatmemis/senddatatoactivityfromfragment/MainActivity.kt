package com.kursatmemis.senddatatoactivityfromfragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kursatmemis.senddatatoactivityfromfragment.databinding.ActivityMainBinding

/**
 * Fragment'tan Activity'e Veri Gönderme:
 * Bunun için MainActivity class'ı içinde bir method oluşturur(setName) ve bu method parametresinde
 * gönderilmesini istediğimiz verileri belirtiriz. Ardından Fragment class'ı içinde getActivity()
 * methodunu kullanarak fragment'ın o an ilişkili olduğu activity objesini alırız.
 * En sonunda bu obje üzerinden MainActivity'de tanımladığımız methodu(setName) çağırır ve göndermek
 * istediğimiz verileri bu method'a argüman olarak yollarız.
 */

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var name: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        createFragment()

    }

    fun setName(name: String) {
        this.name = name

        binding.nameDataTextView.text = name

    }

    private fun createFragment() {
        val fragmentOne = FragmentOne()
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()

        transaction.add(R.id.container, fragmentOne, "fragmentOne")

        transaction.commit()
    }


}