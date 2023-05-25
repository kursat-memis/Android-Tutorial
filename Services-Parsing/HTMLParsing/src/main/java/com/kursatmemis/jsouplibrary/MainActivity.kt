package com.kursatmemis.jsouplibrary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

/**
 * 1. Dependencies kısmına ilgili implementi yaz.
 * 2. Manifest'de network izni al. (Herhangi bir Programın internete bağlandığı herhangi bir yeri varsa bunu yapmalıyız.)
 * 2. Result adında bir class oluşturalım.
 */

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val result = Result()
        // Thread'lerle alakalı bir durumdan dolayı, result.news()'i runnable içine atıyoruz.
        val run = Runnable {
            result.news()
        }
        Thread(run).start()
        // Thred'lenmiş ifadeler, alttaki satırların calismasina engel değildir.
        // Yani result.news() calismadan önce, alttaki log komutu calisabilir ve calisacaktirda...
        Log.d("mkm_msg", "Burası result.news()'den sonra calisacak.!")


    }
}