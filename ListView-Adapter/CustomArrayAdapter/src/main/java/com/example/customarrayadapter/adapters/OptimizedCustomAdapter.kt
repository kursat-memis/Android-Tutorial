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
 * Optimizasyonlar:
 * 1. convertView kullanılarak layout inflation işleminin her getView çağrısında kullanılması
 * engellendi ve performanstan kazanç sağlandı.
 *
 * 2. Bir ViewHolder Design Pattern kullanılarak, her getView çağrısı sırasında findViewById
 * methodunun çalıştırılmasının ve her defasında bir textView objesinin bellekte oluşturulmasının
 * önüne geçildi.
 */

var countInflation = 0

class OptimizedCustomAdapter(
    private val context: AppCompatActivity,
    private val data: List<String> = ItemData.createData()
) :
    ArrayAdapter<String>(context, R.layout.custom_layout, data) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater = context.layoutInflater
        val customView: View?
        val viewHolder: ViewHolder

        if (convertView == null) {
            customView = layoutInflater.inflate(R.layout.custom_layout, parent, false)
            viewHolder = ViewHolder()
            viewHolder.textView = customView.findViewById(R.id.my_text_view)
            customView.tag = viewHolder
            Log.e("mKm - getView", "Inflation Count: ${++countInflation}")
        } else {
            customView = convertView
            viewHolder = customView.tag as ViewHolder
            Log.e(
                "mKm - getView",
                "convertView: ${convertView?.findViewById<TextView>(R.id.my_text_view)?.text} " +
                        "for ${data[position]}"
            )
        }

        viewHolder.textView.text = data[position]

        return customView!!
    }

    class ViewHolder {
        lateinit var textView: TextView
    }

}
