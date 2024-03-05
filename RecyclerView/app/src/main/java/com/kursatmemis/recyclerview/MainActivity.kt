package com.kursatmemis.recyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.kursatmemis.recyclerview.databinding.ActivityMainBinding

/**
 * Structure of RecyclerView:
 * 1. Item Layout: RecyclerView'ın her bir item'ını temsil edecek olan xml dosyası.
 * 2. Data Class: RecyclerView üzerindeki itemlar için bilgi tutan custom modelimiz.
 * 3. Adapter: RecyclerView'ın ana kodudur. Gerekli methodlar bunun içerisinde sağlanır.
 *             En temel tanımıyla adapter; Verileri bir formata sokan ve ekran üzerindeki view'larda
 *             gösterilmesini sağlayan bir yapıdır.
 * 4. ViewHolder: Adapter class'ı içinde tanımladığımız bir inner class. Bu, item layout'daki
 *    view'ların referanslarını tutar. Bu referansları kullanarak programın çalışması sırasında
 *    item'lar üzerinde dinamik olarak görüntülecek verileri belirleriz. Bizi findViewById()
 *    methodunu sürekli olarak çağırmaktan kurtararak daha performanslı bir uygulama yazmamızı
 *    sağlar.
 */

/**
 * Nasıl Kullanılır?
 *
 * Not: Eğer ekranda sadece RecyclerView göstereceksek root view'ı Constraint Layout yapmak yerine
 * FrameLayout yapmak bize daha çok pratiklik sağlayacaktır. Ek olarak root view'ı direkt
 * RecyclerView yapmak bazen görüntülenme sorunlarına sebep olabilmektedir.
 *
 * Not: RecyclerView'daki item'ların tasarımı için CardView kullanırsak daha hoş bir görüntü
 * elde edebiliriz.
 *
 * Not: RecyclerView, ListView gibi bir item'a dokunulduğunda default olarak bir ripple efect
 * sunmuyor. Bunun gerçekleşmesi için RecyclerView'ın Item'larını temsil eden Layout dosyasına
 * gidip, root element'e aşağıdaki attr.'ler tanımlanmalı:
 *
 *  android:focusable="true"
 *  android:clickable="true"
 *  android:foreground="?android:attr/selectableItemBackground"
 *
 * 1. RecyclerView'ın gösterilmesi istenen Activity/Fragment'ın xml dosyasında recyclerview
 *    tanımlanır.
 * 2. RecyclerView'ın item'larını temsil edecek bir xml dosyası oluşturulur.
 * 3. RecyclerView'ın item'ları üzerindeki bilgileri temsil edecek bir model oluşturmak adına
 *    bir data class oluşturulur.
 * 4. RecyclerView için bir adapter class'ı oluşturulur. [Gerekli açıklamalar MyAdapter'da yapıldı.]
 * 5. RecyclerView'ın layoutManager'ı ve adapter'ı set edilir.
 *
 */

/**
 * Recycle Mantığı:
 * Diyelim ki ekranımız aynı anda sadece 5 item gösterebilecek büyüklükte. Uygulama ilk açıldığında
 * bu 5 item oluşturulur ve ekranda gösterilir. Ardından kullanıcı listeyi scroll yaptığında
 * ekrandan çıkan item, ekrana yeni girecek item olarak kullanılır. Yani yeni bir item oluşturulmaz.
 * Bu da performans'ın optimize edilmesini sağlar.
 *
 * Not: Dökümanlara Göre Şöyle:
 * RecyclerView ilk açılışta;
 * ekranda gösterilebilecek itemlar + ekran aşağı kaydırıldığında ekranda gözükecek olan 2 item'ı
 * bellekte oluşturur. Kullanıcı listeyi 2 item kadar aşağı kaydırdığında, ekrandan çıkan ilk iki
 * item recycle edilmez çünkü ekrana yeni girmiş olan 2 item daha öncesinde oluşturulmuştu. Ancak
 * kullanıcı ekranı daha da aşağı indirmeye başlarsa artık öğeler recycle edilir.
 *
 * Not: Ancak bu her zaman böyle olmuyor. Mesela ekranda gösterilecek item sayısı + 1 olarakta
 * recyclerview öğeleri başatabiliyor. Ama genel kural şu şekilde:
 * 2 + number of views on the screen + 2
 *
 */

/**
 * Handle Item Click on RecyclerView:
 * onBindViewHolder() methodu içinde, RecyclerView'ın her item'ı için bir clickListener ataması
 * yapabiliriz. Ancak burada bir maliyet söz konusu. Çünkü onBindViewHolder() her item için
 * çalışan bir method ve her çalışmasında clickListener ataması yapmak memory'i gereksiz yere
 * yorar. Aslında şöyle ki clickListener ataması yapmak belleği çok fazla yoran bir işlem değildir
 * ancak eğer ki bizim item'larımız üzerinde birçok view var ise ve bu view'larında bir çoğunda
 * clickListener ataması yapma ihtiyacımız varsa bu durumda her item için clickListener ataması yapmak
 * performansı etkileyebilir.
 *
 * Buna çözüm olarak, clickListener atamasını ViewHolder class'ının init blogu içinde yapmamız gerekir.
 * Çünkü ViewHolder class'ının init blogu, yalnızca ViewHolder objesi oluşturulduğunda çalışacaktır.
 * Ve ViewHolder objesi, sadece uygulama ilk açıldığında ekranda gözüken item'lar için oluşacaktır.
 * Ardından kullanıcı ekranı aşağı-yukarı kaydırdığında recycle olayı olacağı için yeni bir
 * viewHolder objesi vs. oluşmayacaktır. Yani diyelim ki 1000 elemandan bulunan bir listemiz var ve
 * ekranda sadece 10 item'ı aynı anda gösteriyoruz. Uygulama ilk açıldığında sadece ekranda bulunan
 * 10 item için viewHolder objesi oluşturulacak ve diğer 990 item, bu 10 item'ın recycle edilmesiyle
 * elde edileceği için 990 item için artık viewHolder objesi oluşturulmayacak. Bu sayede biz sadece
 * 10 kere clickListener ataması yapmış olacağız.
 *
 * ViewHolder class'ı içinde item'ın position bilgisini almak:
 * Bunun için kullanabileceğimiz iki property var:
 * 1. adapterPosition:
 *
 * 2. layoutPosition:
 */

class MainActivity : AppCompatActivity(), MyAdapterWithViewBinding.ItemOnClickListener {

    private lateinit var dataSource: ArrayList<String>
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dataSource = createDataSource()

        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this,)

        /*val adapter = MyAdapter(dataSource)
        recyclerView.adapter = adapter*/

        /*val adapter = MultiViewAdapter(dataSource)
        recyclerView.adapter = adapter*/

        val adapter = MyAdapterWithViewBinding(dataSource, this)
        recyclerView.adapter = adapter

    }

    private fun createDataSource(): ArrayList<String> {
        val arrayList = ArrayList<String>()

        for (number in 1..100) {
            val item = number.toString()
            arrayList.add(item)
        }

        return arrayList
    }

    override fun onClick(text: String, adapterPosition: Int, layoutPosition: Int) {
        Toast.makeText(this, "AP: $adapterPosition LP: $layoutPosition", Toast.LENGTH_SHORT).show()
    }

}

