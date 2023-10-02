package com.kursatmemis.viewbinding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.kursatmemis.viewbinding.databinding.ActivityMainBinding

/**
 * Not: Eğer Binding class'ının oluşmasını istemediğimiz xml dosyaları var ise; bu xml dosyalarının
 * root elementine 'tools:viewBindingIgnore="true"' attribute'u eklenir. Bu sayede, viewBinding'e
 * ihtiyacımız olmayan xml dosyaları için bir Binding class'ı oluşmayacak ve daha iyi bir performans
 * ile projemizi gerçekleştirmiş olacağız.
 */

/**
 * Activity'de ViewBinding Kullanımı:
 * 1. build.gradle(Module:App) dosyasında android scope'u içerisine aşağıdaki kod eklenir.

    buildFeatures {
         viewBinding = true
    }

 * 2. Activity'de, binding objesi tanımlanır.
 * (Layout dosyasının ismine göre: activity_main.xml — — — — — > ActivityMainBinding)
 * 3. binding objesi onCreate() methodu içerisinde başlatılır.
 * 4. setContentView methodu için argüman olarak binding.root gönderilir.
 * 5. Artık bu binding objesi üzerinden layout dosyasındaki componentlere erişebiliriz.
 */

/**
 * Fragment'da ViewBinding Kullanımı:
 * MyFragment adlı fragment class'ında nasıl kullanıldığı anlatıldı.
 */

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textView.text = "ViewBinding in Activity"
    }
}
