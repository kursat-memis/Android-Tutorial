package com.kursatmemis.recyclerview

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(private val dataSource: ArrayList<String>) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    /**
     * Bu class'da RecyclerView'ın item'ları içinde bulunan view'ların referansları alınır.
     * Bu sayede item'ların ekranda gösterilmesi sırasında her item için, o item'ın içinde bulunan
     * view'ların referanslarına erişmek için findViewById() methodunu kullanarak performans kaybı
     * yaşamamış oluruz.
     *
     * Diyelim ki elimizde 100 item var ve her item'da 2 adet TextView bulunmakta. Ayrıca ekranda da
     * aynı anda sadece sadece 5 item aynı anda görüntülenebiliyor olsun.  Her item için
     * findViewById kullandığımız taktirde toplamda 200 kere bu methodu çağırmak durumunda kalacağız.
     * Ancak ViewHolder kullandığımız durumda, RecyclerView ilk çalıştığında 5 + 2 item için
     * findViewById() methodunu çağıracak ve toplamda 14 kere çağırmış olacak. Ardından item'ları
     * recycle olarak kullandığı için bir daha findViewById() methodunu çağırmaya gerek duymayacak.
     * İşte buradaki gibi 200 yerine 14 kere findViewById() methodunun çağrımıyla birlikte
     * performanstan kazanç sağlamış olacağız.
     */
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            Log.w("mKm - krst", "MyViewHolder object created.")
        }

        val textView = itemView.findViewById<TextView>(R.id.textView)
    }

    /**
     * Bu method, RecyclerView'ın yeni bir item'ı oluşturacağı zaman çağrılır.
     * Bu method içinde, ekranda gösterilecek item'ın içinde bulunan view'ların referanslarını
     * tutacak olan ViewHolder objesi oluşturulur ve bu obje return edilir.
     *
     * Not: ViewHolder objesi oluşturmak için bizden bir view objesi beklenmektedir. Bunun
     * için RecyclerView'ın item'larını temsil eden layout dosyası inflate edilir ve bunun
     * sonucunda elde edilen view objesi, ViewHolder objesi oluşturmak için kullanılır.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        Log.w("mKm - krst", "onCreateViewHolder runned.")
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_card_view, parent, false)
        return MyViewHolder(view)
    }

    /**
     * Bu method, dataSource'daki belirli pozisyondaki öğeyi, öğeyi temsil eden itemView'a bağlamak
     * için kullanılır. Bunu yapmak için methodun bize parametre olarak verdiği viewHolder objesini
     * kullanırız.
     * Bu obje sayesinde, ViewHolder'da tanımladığımız view referanslarına erişebilir ve bu
     * referansları kullanarak view'ların üzerinde gösterilecek data'ları set edebiliriz.
     */
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Log.e("mKm - krst", "onBindViewHolder runned.")
        holder.textView.text = dataSource[position]
        holder.itemView.setOnClickListener {
            Toast.makeText(
                holder.textView.context,
                holder.textView.text.toString() + " clicked",
                Toast.LENGTH_SHORT
            ).show()
        }

    }

    /**
     * RecyclerView'da kaç tane öğe gösterileceğini belirtmek için kullanılır.
     * Yani adapter'ın bağlı olduğu veri kümesinin öğe sayısını return ederiz.
     */
    override fun getItemCount(): Int {
        // Log.d("mKm - krst", "getItemCount runned.")
        return dataSource.size
    }

}
