package com.kursatmemis.viewbinding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kursatmemis.viewbinding.databinding.ActivityMainBinding

/**
 * ViewBinding Nasıl Kullanılır?
 * 1. build.gradle(Module:App) dosyasına aşağıdaki kod eklenir.
 *
    buildFeatures {
         viewBinding = true
    }
 *
 * 2. Activity'de, binding objesi tanımlanır.
 * (Layout dosyasının ismine göre: activity_main.xml — — — — — > ActivityMainBinding)
 * 3. onCreate methodu içinde binding objesine değer atanır.
 * 4. setContentView methodu için argüman olarak binding.root gönderilir.
 * 5. Artık bu binding objesi üzerinden layout dosyasındaki componentlere erişebiliriz.
 *
 * Not: Eğer bir xml dosyamızın viewBinding ile bağlanmasını istemiyorsak, yani; bu xml dosyası
 * için bir Binding class'ı oluşturulmasını istemiyorsak, o xml dosyasının root elementine
 * 'tools:viewBindingIgnore="true"' attribute'unu ekleriz.
 */

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    // private lateinit var bindingTest: TestBinding
    // Test xml'inde tools:viewBindingIgnore="true" kullanıldığından dolayı Test xml'i için
    // binding class'ı oluşmaz.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textView.text = "View Binding!"
    }
}