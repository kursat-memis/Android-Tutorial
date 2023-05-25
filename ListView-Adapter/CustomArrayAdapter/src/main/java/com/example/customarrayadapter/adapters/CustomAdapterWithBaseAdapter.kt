package com.example.customarrayadapter.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.customarrayadapter.R
import com.example.customarrayadapter.datas.ItemData

/**
 * Implement Edilmesi Gereken Methodlar:
 *
 * getCount(): ListView üzerinde görüntülenecek olan item sayısını return eder.
 * Yani veri kaynağından alınan öğelerin sayısını return eder.
 *
 * getItem(): Belirli bir konumda bulunan öğeyi return eder. Yani, veri kaynağındaki
 * belirli index'deki elemana erişmeyi sağlar.
 *
 * getItemId(): Belirli bir konumda bulunan öğenin benzersiz bir kimliğini döndürür.
 * Genellikle database işlemlerinde kullanılır.
 *
 * getView(): ArrayAdapter class'ı ile aynı.
 */


class CustomAdapterWithBaseAdapter(context: Context) : BaseAdapter() {

    val dataSource: List<String>
    val context: Context

    init {
        dataSource = ItemData.createData()
        this.context = context
    }

    override fun getCount(): Int {
        return dataSource.size
    }

    override fun getItem(position: Int): Any {
        return dataSource[position]
    }

    override fun getItemId(position: Int): Long {
        return 0 // Genellikle Database işlemlerinde kullanılıyor. Onun dışında 0 return edilebilir.
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val layoutInflater = LayoutInflater.from(context)
        val customView: View
        val viewHolder: ViewHolder

        if (convertView == null) {
            customView = layoutInflater.inflate(R.layout.custom_layout, parent, false)
            viewHolder = ViewHolder()
            viewHolder.textView = customView.findViewById(R.id.my_text_view)
            customView.tag = viewHolder

        } else {
            customView = convertView
            viewHolder = customView.tag as ViewHolder
        }

        viewHolder.textView.text = dataSource[position]

        return customView
    }

    class ViewHolder {
        lateinit var textView: TextView
    }

}