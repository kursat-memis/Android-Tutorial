package com.kursatmemis.dagger

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kursatmemis.dagger.dependency_injection.components.DaggerAppComponent
import com.kursatmemis.dagger.dependency_injection.consumers.Internet
import com.kursatmemis.dagger.dependency_injection.consumers.Kargo
import javax.inject.Inject

/**
 * Gerekli Kurulumlar:
 * build.gradle(module) dosyasında, plugins kısmına;

    id("kotlin-kapt")

 * dependencies kısmına ise;

    implementation("com.google.dagger:dagger:2.48")
    kapt("com.google.dagger:dagger-compiler:2.48")

 * satırları eklenir.
 * Not: Eğer gradle'da 'app:kaptGenerateStubsDebugKotlin' hatası çıkarsa, build.gradle(module)
 * dosyasında ilgili kodları aşağıdaki kodlarla değiştir;

    compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
    jvmTarget = "17"
    }

 */

/**
 * Nasıl Kullanılır?
 * 1. Yukarıda belirtilen gerekli kurulumlar yapılır.
 * 2. Bağımlılıkları kullanacak olan class'lar oluşturulur. (Kargo, Internet)
 * 3. Bağımlılıkların nasıl oluşturulacaklarını ve sağlanacaklarını belirten modül class'ı
 * oluşturulur. (AppModule)
 * 4. Bağımlılıkların enjekte işlemini yönetecek olan interface oluşturulur. (AppComponent)
 * 5. Dagger tarafından otomatik olarak başlatılmasını istediğimiz objeler, @Inject annonation'ı
 * kullanılarak tanımlanır.
 * (Dagger bu objeleri dışardan erişerek başlatacağı için bu objeleri private tanımlayamayız.)
 * 6. Bu işlemler neticesinde Dagger tarafından otomatik olarak DaggerAppComponent adında bir
 * class oluşturulacaktır. (Dagger + Your Component Name)
 * Bu class kullanılarak dagger'ın bağımlılıkları yönetmesini başlatılır.
 * Not: DaggerAppComponent class'ı android studio'daki bug'dan dolayı bazen otomatik olarak gelmeye-
 * bilir. Bunun için projeyi rebuild yap.
 */

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var kargo: Kargo
    @Inject
    lateinit var internet: Internet

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        DaggerAppComponent.create().inject(this)

        kargo.kargoyuGonder()
        internet.basvuruYap()

    }

}