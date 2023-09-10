package com.kursatmemis.fragmentveritasima

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kursatmemis.fragmentveritasima.databinding.ActivityMainBinding

/**
 * Activity'den Fragment'a Veri Gönderme:
 * Bunun için temel mantık şudur;
 * Activity içerisinde oluşturulan fragment objesi(fragmentOne) kullanılarak bu obje üzerinden
 * fragment'a ait methodlara ulaşılarak bu methodlara istediğimiz veriyi gönderebiliriz.
 *
 * Bunun için 2 yöntem var;
 * 1. Javadaki Fragment class'ında var olan setArguments ve getArguments methodlarını kullanmak
 *    (Bu işlem sendDataToFragmentByUsingBundle methodunda anlatıldı.)
 * 2. Kendi oluşturduğumuz fragment class'ı içinde bir method tanımlamak.
 *    (Bu işlem sendDataToFragmentByUsingMySetMethod methodunda yapıldı.)
 */

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.sendDataToFragmentButton.setOnClickListener {
            val name = binding.nameEditText.text.toString()

            // sendDataToFragmentByUsingBundle(name)
            sendDataToFragmentByUsingMySetMethod(name)
        }

    }

    /**
     * FragmentOne class'ı içinde setName adında bir method oluşturur ve bu method ile FragmentOne
     * class'ındaki class attribute'lerine değerler atayabiliriz. Bu sayede MainActivity'den,
     * Fragment'a veri taşıyabiliriz.
     */

    private fun sendDataToFragmentByUsingMySetMethod(name: String) {
        val fragmentOne = FragmentOne()

        fragmentOne.setName(name)

        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.add(R.id.container, fragmentOne, "fragmentOne")
        transaction.commit()
    }

    /**
     * Kendi oluşturduğumuz FragmentOne class'ını, java tarafından oluşturulmuş olan Fragment
     * class'ından extend ettiğimiz için bu class içindeki setArguments ve getArguments methodlarına
     * erişebiliriz. setArguments methodu bizden bundle tipinde bir parametre bekler ve MainActivity
     * içinde fragmentOne objesi üzerinden taşınmasını istediğimiz verileri bundle içine koyar ve
     * bu bundle objesini setArguments methoduna parametre olarak geçebiliriz. Ardından FragmentOne
     * class'ı içinde getArguments methodunu kullanarak gönderilen bundle objesini alır ve bundle
     * objesi içindeki verileri kullanabiliriz.
     */
    private fun sendDataToFragmentByUsingBundle(name: String) {
        val fragmentOne = FragmentOne()

        val bundle = Bundle()
        bundle.putString("name", name)
        fragmentOne.arguments = bundle

        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.add(R.id.container, fragmentOne, "fragmentOne")
        transaction.commit()

    }
}