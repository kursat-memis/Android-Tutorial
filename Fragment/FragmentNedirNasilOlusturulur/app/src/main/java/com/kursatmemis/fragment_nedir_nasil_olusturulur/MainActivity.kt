package com.kursatmemis.fragment_nedir_nasil_olusturulur

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

/**
 * Fragment Oluşturma:
 * 1. Fragment class'ını extend eden bir kotlin class'ı oluşturulur. (FragmentOne)
 * 2. Fragment tasarımını tutan bir xml dosyası oluşturulur. (fragment_one.xml)
 * 3. Bu class içerisinde, onCreateView methodu override edilir ve ekranda gösterilmek istenen
 * fragment'ın xml dosyası, onCreateView methodunda return edilir.
 *
 * Not: Fragment class'ını manuel olarak oluşturmak yerine, aynı bir activity oluşturur gibi
 * 'blank fragment' oluşturulup, onCreateView methodu hariç diğer otomatik gelen kodları silerekte
 * aynı yapıyı elde edebilirdik.
 *
 * 4. Fragment class'ını extend eden class'dan bir obje oluşturulur.(FragmentOne objesi)
 * 5. Bir fragmentManager objesi oluşturulur. (supportFragmentManager)
 * 6. fragmentManager objesi üzerinden bir transaction oluşturulur. (fragmentManager.beginTransaction())
 * 7. transaction.add methodu kullanılarak ekran üzerinde gösterilmesini istediğimiz fragment
 * eklenir.
 * 8. Transaction üzerinden yapılan işlemler commit methodu kullanılarak onaylanır. (transaction.commit())
 *
 * transaction.add(containerViewId: Int, fragment: Fragment, tag: String) Methodu:
 * containerViewId: Belirtilen fragment'ın üzerinde gözükeceği container id'si.
 * fragment: Gösterilmek istenilen fragment objesi.
 * tag: Fragment'ı temsil eden bir tag.
 * (Bu tag daha sonra ekranda gösterilen fragment'ı kaldırmak, gizlemek vb. aksiyonları
 * gerçekleştirmek için kullanılacak.)
 */

// Fragment ekleme-çıkarma işlemleri için transaction başlatman lazım.
// Transaction başlatman için manager başlatman lazım.
// Manager başlatman için miras olarak gelen supportFragmentManager kullanman lazım.
// Miras olarak gelen supportFragmentManager üzerinden beginTransaction() methodunu çağırarak
// transaction başlatabilirsin.

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentOne = FragmentOne()
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()

        transaction.add(R.id.root_element, fragmentOne, "FragmentOne")

        transaction.commit()

    }
}