package com.example.customarrayadapter.adapters

import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.customarrayadapter.R
import com.example.customarrayadapter.datas.ItemData

/**
 * getView Methodu Çalışma Mantığı:
 *
 * Öncelikle program ilk açıldığında ListView'ın (veya herhangi bir ViewGroup) üzerinde kaç tane
 * eleman gözüküyorsa ve kullanıcı ListView'ı aşağı kaydırdığında hangi elemanı görecekse
 * o elemanların hepsi için teker teker çalışır. Örneğin ListView üzerinde 5 tane
 * eleman sığabiliyorsa bu method 5 kere çalışır ve bu elemanları ListView'a ekler.
 * Buna ek olarak kullanıcı ListView'ı aşağı doğru kaydırdığında görüntülenecek olan
 * View içinde çalışarak bu View'ıda hazırlamış olur.
 *
 * Buraya kadar yapılan işlemlerde convertView parametresi 'null' değerine sahiptir. Çünkü henüz
 * convert edilecek bir View yoktur.
 *
 * Bundan sonra kullanıcı ListView'ı aşağı veya yukarı kaydırdığı zaman ekranda gözükecek olan
 * item için çalışacak getView methodunun convertView parametresine, ekrandan çıkacak olan
 * View objesi argüman olarak gönderilir.
 *
 * Ayrıca ekrandan çıkan View objeleri bellekten silinirler.
 */

/**
 * getView(position: Int, convertView: View?, parent: ViewGroup): View
 *
 * Params:
 * position: Datasource'daki öğenin konumu temsil eder.
 * 0'dan başlar ve teker teker size-1'e kadar gider.
 *
 * convertView: Yeniden kullanılacak olan bir view varsa bu view'ı temsil eder. Eğer yeniden
 * kullanılacak view yoksa bu parametre null'dır.
 *
 * parent: View'ın ekleneceği ViewGroup'u temsil eder. Bu parametreye, argümanı sistem otomatik
 * olarak kendisi yollar. Adapter, hangi ViewGroup'un adapter'ına set edilmişse, bu ViewGroup
 * argüman olarak gönderilir.
 *
 * Return -> View: getView methodundan return edilen View objesi, ViewGroup'a eklenir.
 */

/**
 * Optimizasyon!!! (Önemli)
 *
 * Burada yazdığımız class ile oluşturulan adapter, sistemi verimli olarak kullanmaz.
 * Çünkü:
 *
 * 1. Layout inflation işlemi maliyetli bir işlemdir ve her getView methodu çalıştığında
 * bu işlem yapılacaktır. Buda bize büyük maliyet getirir.
 *
 * 2. Kullanıcı ListView'ı aşağı-yukarı hareket ettirdiğinde, ekrandan çıkan View objeleri Garbage
 * Collector (GB) tarafından silinecek ve bu View'lar tekrar görüntüleneceği zaman tekrar bu View
 * objeleri oluşturulacaktır. Burada silinme ve oluşturulma işlemleri tekrar tekrar yapıldığında
 * bize maliyet getirir.
 *
 * 3. CustomLayout dosyasındaki textView objesi her getView methodu çalıştığında oluşacaktır.
 * Bundan dolayı sürekli bellekte bir obje oluşacağı için performans düşecektir. Ayrıca textView
 * objesinin oluşturulmasında findViewById methoduda kullanılmaktadır ve bu method maliyetli bir
 * işlemdir. Her getView methodu çağrısında findViewById methodunun çalışması bize maliyet getirir.
 */

var countGetView = 0

class UnoptimizedCustomAdapter(
    private val context: AppCompatActivity,
    private val data: List<String> = ItemData.createData()
) :
    ArrayAdapter<String>(context, R.layout.custom_layout, data) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater = context.layoutInflater
        val customView = layoutInflater.inflate(R.layout.custom_layout, parent, false)
        val textView = customView.findViewById<TextView>(R.id.my_text_view)
        textView.text = data[position]

        Log.w("custom-adapter", "getView methodu çalıştı.")

        return customView
    }

}