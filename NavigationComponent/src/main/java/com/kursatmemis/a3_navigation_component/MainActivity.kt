package com.kursatmemis.a3_navigation_component

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

/**
 * Navigation Component:
 * Fragment ve sayfalar arasındaki geçişleri daha pratik bir hale getirir.
 * Bu yapının kullanabilmek için fragment kullanmamız gerekiyor.
 *
 * Nasıl Kullanılır?
 * 1. Aşağıdaki kodlar dependencies'e eklenir.
 * implementation "androidx.navigation:navigation-fragment-ktx:2.3.3"
 * implementation "androidx.navigation:navigation-ui-ktx:2.3.3"
 *
 * 2. res klasörü altında, navigation klasörü oluşturulur.
 * 3. Fragment'lar oluşturulur.
 * 4. Navigation dosyasına oluşturulan fragment'lar eklenir.
 * 5. activity_main.xml içerisine, NavHostFragment eklenir ve bu ekleme sırasında navigation
 * dosyası, NavHostFragment'a baglanir.
 */






class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}