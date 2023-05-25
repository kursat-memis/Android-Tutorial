package com.example.customarrayadapter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListAdapter
import android.widget.ListView
import com.example.customarrayadapter.adapters.CustomAdapterWithBaseAdapter
import com.example.customarrayadapter.adapters.OptimizedCustomAdapter
import com.example.customarrayadapter.adapters.UnoptimizedCustomAdapter

/**
 * Neden Custom Bir Adapter Yazarız?
 * Standart Adapter ile sadece bir TextView'ın içine hükmedebiliyoruz.
 * Ancak daha karmaşık tasarımlarda (içinde birden fazla TextView-ImageView vb. bulunduran)
 * her bir component'e erişebilmek için Custom bir adapter yazmalıyız.
 */

/**
 * Nasıl Kullanılır?
 * 1. Model Oluşturulur: Eğer gerek duyulursa data source'daki veriler için bir model class'ı
 * oluşturulur.
 * 2. Data Source Oluşturulur: Data'ları tutan bir obje tanımlanıp içine objeler koyulur.
 * 3. Kendi tasarımımızı tutan bir xml layout dosyası oluşturulur.
 * 4. activity_main.xml içerisinde bir ListView tanımlanır.
 * 5. Custom Adapter yazılır.
 *
 * Custom Adapter Nasıl Yazılır?
 * 1. Bir class oluşturulur ve bu class ArrayAdapter class'ından extends edilir.
 * 2. ArrayAdapter class'ının getView methodu override edilir ve bu methodun içeriği doldurulur.
 *    (İlgili class'ta nasıl doldurulacağı ve çalışma mantığı anlatıldı.)
 *
 * Not: Custom bir Adapter yazmak için kendi oluşturduğun class'ı BaseAdapter veya ArrayAdapter
 * class'ından miras alabilirsin. Hangisinden miras alırsan al aralarında performans farkı olmaz
 * ancak daha az kodlama yapmak için ArrayAdapter class'ının miras alınması daha iyi olabilir.
 */

class MainActivity : AppCompatActivity() {
    private lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.listView)
        // val customAdapter = useBaseAdapter()
        // val customAdapter = useUnOptimizedCustomAdapter()
        val customAdapter = useOptimizedCustomAdapter()
        listView.adapter = customAdapter
    }

    private fun useOptimizedCustomAdapter(): OptimizedCustomAdapter {
        val customAdapter = OptimizedCustomAdapter(this@MainActivity)
        return customAdapter
    }

    private fun useUnOptimizedCustomAdapter(): UnoptimizedCustomAdapter {
        val customAdapter = UnoptimizedCustomAdapter(this@MainActivity)
        return customAdapter
    }

    private fun useBaseAdapter(): CustomAdapterWithBaseAdapter {
        val customAdater = CustomAdapterWithBaseAdapter(this@MainActivity)
        return customAdater
    }
}