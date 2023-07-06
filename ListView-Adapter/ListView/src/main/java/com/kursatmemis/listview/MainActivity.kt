package com.kursatmemis.arrayadapter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast

/**
 * ListView Nedir?
 * Kullanıcının çok fazla veriyi kolay bir şekilde görebileceği bir view'dır.
 * Kullanıcı ekranın sonuna geldiğinde, yeni bilgiler üretilir ve listview üzerinde gösterilir.
 * Listview sayesinde, comlex ve çok sayıda veriyi kullanıcıya sunabiliriz.
 */

/**
 * Nasıl Kullanılır?
 * 1. Veri kaynağı (Data Source) hazırlanır. (Array - ArrayList - Database vb.)
 * 2. Listview üzerindeki satırları belirttiğimiz layout dosyası hazırlanır.
 * 3. Bir adapter oluşturulur ve listview'ın adapter'ına oluşturulan adapter atanır.
 * 4. Kullanıcının listview üzerinde bir öğeye tıkladığında ne olacağını belirtmemiz için
 * OnItemClickListener methodu kullanılır.
 *
 * OnItemClickListener(adapterView: AdapterView, view: View, position: Int, id: Long)
 * adapterView: Tıklanan item'ın bulunduğu view'ı temsil eder. Örneğin ListView üzerindeki bir
 * item'a tıklanırsa, ListView temsil edilir.
 * view: ListView üzerinde tıklanan View'ı temsil eder.
 * position: ListView üzerinde tıklanan item'ın konumunu belirler. (İlk item için konum = 0)
 * id: Tıklanan item'ın bir benzersiz kimliği (id) varsa, bu parametreyle elde edilir. Genellikle
 * veri tabanı işlemlerinde kullanılır.
 */

/**
 * Adapter Nedir?
 *
 * Bir veri kaynağı (array, arraylist vb.) alır ve bu veri kaynağındaki verileri view’lara dönüştürür.
 * Sonrada bu view’ları bir AdapterView(ListView, GridView, Spinner ve Gallery) içerisine atar.
 *
 * Adapter, bir data source ile data'ya ihtiyacı olan view'ı birbirine bağlamaya yarayan
 * bir yapıdır. ListView gibi içerisinde bir grup data tutan yapılara, bu data'ları bir adapter
 * aracılığıyla veririz. Adapter bir data source'deki verileri, görsel öğelerde kullanmaya
 * uygun hale getirir.
 *
 * Yani adapter, bizim veri kaynağımızdaki verileri alıp formatlayan sonrada bir veri tutucu objeye
 * bu verileri gönderen bir yapıdır.

ArrayAdapter Constructor Params:

 1- context: Context ister.

 2- resource: Bu parametre, bir layout dosyasının id'sini ister. Bu Layout dosyasının root elementi
 bir TextView olmalı. Adapter, veri kaynağından gelen verileri bu TextView üzerine yazacaktır.

 * Not: Eğer 3. parametre olan textViewRecourceId parametresine bir argüman yollanmayacak ise resource
 * parametresine gönderdiğimiz layout dosyasının root element'i bir textview olmalı.
 * Aksi halde program patlayacaktır. Ancak textViewRecourceId parametresine argüman yollanırsa,
 * resource parametresine gönderilen layout dosyasının root elementinin TextView olmasına gerek yoktur.

 3- textViewRecourceId: Bir textview id'si ister.
 Bunun şundan dolayı kullanırız: Eğer resource'de belirtilen layout dosyasının root elementi bir
 TextView değilse, adapter, verileri hangi textview üzerine yazacağını bilemez. Bu parametreye,
 resource parametresine argüman olarak gönderdiğimiz layout dosyasının içindeki hangi textview'ın
 üzerine verilerin yazılmasını istiyorsak o textview'ın id'sini göndeririz.

 4- dataSource: Veri kaynağımızı argüman olarak göndeririz.
 */

class MainActivity : AppCompatActivity() {

    // DataSource
    private val dataSource = arrayOf(
        "Apple",
        "Melon",
        "Watermelon",
        "Cherry",
        "Banana",
        "Cucumber",
        "Tomato",
        "Strawberry",
        "Grape",
        "Orange",
        "Lemon",
        "Kiwi",
        "Mango",
        "Pineapple",
        "Pear",
        "Lettuce",
        "Broccoli",
        "Carrot",
        "Onion",
        "Potato",
        "Mango",
        "Avocado",
        "Mushroom"
    )
    private lateinit var listView: ListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listView = findViewById(R.id.listView)

        // Aşağıda, resource parametresine argüman olarak android'in hazır olarak sunduğu layout
        // dosyasını gönderdik.
        // val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dataSource)

        // Aşağıda, resource parametresine kendi hazırladığımız ve root elementi TextView olan bir
        // layout dosyasını parametre olarak gönderdik.
        // val adapter = ArrayAdapter(this, R.layout.my_text_view_layout, dataSource)

        // Aşağıda, resource parametresine kendi hazırladığımız ve root elementi TextView OLMAYAN
        // bir layout dosyası gönderdik. Adapter'ın, veri kaynağındaki verileri hangi TextView
        // içerisine yazması gerektiğini ise, textViewRecourceId parametresinde belirttik.
        val adapter = ArrayAdapter(this, R.layout.my_linear_layout, R.id.my_text_view_1, dataSource)
        listView.adapter = adapter

        // Aşağıda, listview üzerindeki item'lara tıklanıldığında ne olacağı belirtilmiştir.
        // AdapterView, listview'ın kendisini temsil eder.
        // view, tıklanan item'ı temsil eder.
        // (Burada tıklanan item'lar my_linear_layout id'sine sahip linearlayout'lardır.)
        listView.setOnItemClickListener { adapterView, view, i, l ->
            val linearLayout = view as LinearLayout
            val text = linearLayout.findViewById<TextView>(R.id.my_text_view_1).text
            Toast.makeText(this@MainActivity, "Text: $text Index: $i", Toast.LENGTH_SHORT)
                .show()
        }

        // Eğer run time'da, adapter'da belirtilen data source üzerinde, add-remove gibi data source'u
        // güncelleyen operasyonlar çağrılmışsa, bu değişikliklerin adapter'a bildirilmesi ve
        // bunun sonucunda listview üzerindeki item'ların güncellenmesi gerekir.
        // Bu değişiklikleri adapter'a bildirmek için aşağıdaki notifyDataSetChanged methodunu
        // kullanırız.
        adapter.notifyDataSetChanged()
    }
}