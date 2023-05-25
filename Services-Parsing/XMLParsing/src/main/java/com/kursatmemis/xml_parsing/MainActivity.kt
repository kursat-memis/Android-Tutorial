package com.kursatmemis.xml_parsing

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode

/**
 * Kodlar üzerinden açıklamalar yapıldı.
 */

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*
         Aşağıdaki 2 satırı kullanmazsan program android.os.NetworkOnMainThreadException fırlatır.
         Bunun sebebi; bu iki satırı kullanmazsak internet sitesine xml için istek atarken
         ana thread kullanılır ve UI tepkisiz hale gelir. Buda hataya sebep olur.

         Bundan dolayı ağ işlemlerini arka plan thread'inde yapmamız gerekiyor ve bunu sağlamak
         için aşağıdaki 2 satırlık kodu kullanıyoruz.
         (Bu iki satırlık kod yerine AsyncTask sınıfını kullanarak bu işlemi gerçekleştirmek
         programın daha performanslı olmasını sağlar.)
         */
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        val xmlResult = XmlResult()
        var arrCatalogs = xmlResult.xmlCatalog()
        val arrCurrencies = xmlResult.xmlCurrency()


    }
}