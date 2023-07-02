package com.kursatmemis.jsoup_html_parsing

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

/**
 * Nasıl Kullanılır?
 * 1. Aşadaki linkten Jsoup'un resmi sitesine gidilir ve dependencies'e eklenecek satır alınarak
 * dependencies'e eklenir.
https://jsoup.org/download
 * 2. Uygulamamızda internet üzerinde bir işlem yapıldığı için manifest dosyasında internet izni
 * verilir.
 * 3. Bir Result class'ı oluşturulur. Bu class içinde internetten verileri çekebilmemizi sağlayan
 * methodları yazacağız. (Result class'ı içinde bu methodların nasıl yazıldığı belirtildi.)
 * 4. Bir thread kullanarak Result class'ı içinde tanımlanan method çağrılır ve bunun neticesinde
 * web sitesinden gerekli veriler alınarak uygulamaya dahil edilir.
 */

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val result = Result()

        val run = Runnable {
            result.news()
        }
        Thread(run).start()
    }
}