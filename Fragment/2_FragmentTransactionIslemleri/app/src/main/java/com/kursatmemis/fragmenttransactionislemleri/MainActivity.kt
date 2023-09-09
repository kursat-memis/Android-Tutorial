package com.kursatmemis.fragmenttransactionislemleri

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.FragmentManager

/**
 * Transaction Types:
 * Add: Bir fragment'ı sıfırdan oluşturarak activity'nin içine ekler.
 *
 * Remove: Fragment'ın tüm içeriği ve durumları (içindeki veriler, değişkenler, durum bilgileri vb.)
 * bellekten kaldırılır ve Fragment Yöneticisi tarafından artık takip edilmez.
 * Yani, remove işlemi Fragment'ı kullanıcı arayüzünden ve bellekten tamamen temizler.
 * Bu nedenle, Fragment'ın verileri ve durumu kalıcı olarak kaybolur.
 * Bu işlemi gerçekleştirdiğinizde, Fragment'ı tekrar ekranınıza getirmek için yeniden
 * oluşturmanız gerekecektir.
 * Bu method çağrıldığında fragment tamamen destroy edilir.
 *
 * Replace: Belirli bir Fragment'ı başka bir Fragment ile değiştirir.
 * Yani, mevcut bir Fragment'ın yerine yeni bir Fragment ekler. Mevcut fragment'ı tamamen destroy
 * eder ve yeni fragment'ı sıfırdan oluştururarak eski fragment'ın yerine ekler.
 *
 * Attach: Daha önce detach edilmiş fragment'ı yeniden UI'da gösterir.
 * Bu method çalıştığında sırasıyla şu methodlar tetiklenir;
 * onCreateView - onViewCreated - onStart - onResume
 * Daha önce detach edilen fragment bellekten tamamen kaldırılmadığı için attach edilen fragment
 * sıfırdan oluşmadı.
 *
 * Detach:
 * Fragment'ı UI'dan geçici olarak kaldırır. Yani, bu Fragment artık ekran üzerinde görünmez
 * hale gelir ve kullanıcı tarafından görüntülenmez.
 * Ancak, Fragment'ın iç durumu (yani, içindeki veriler, değişkenler, durum bilgileri vb.)
 * hala aktif bir şekilde saklanır ve Fragment Yöneticisi tarafından izlenir.
 * Bu nedenle, Fragment'ın verileri kaybolmaz.
 * Detach işlemine girildiğinde, Fragment'ın görünüm hiyerarşisi yok edilir, yani Fragment'ın
 * içindeki görünümler (View) ve bu görünümlere ait veriler bellekten temizlenir. Ancak Fragment
 * kendisi ve iç durumu (yani, içindeki veriler, değişkenler, durum bilgileri vb.)
 * hala fragment manager tarafından saklanır.
 * Genel olarak detach edilen fragment bellekten tamamen silinmez.
 * onPause - onStop - onDestroyView methodları çalışır ve fragment hala bellekte vardır.
 *
 * Not:
 * Eğer fragment görünümünü geçici olarak yok etmek ve ileride tekrar görünümünü oluşturup
 * göstermek istiyorsanız, attach ve detach işlemlerini kullanabilirsiniz.
 * Eğer fragment'ı tamamen kaldırmak ve ilerde bu fragment'ı tekrar kullanmak istediğimizde yeni
 * bir fragment oluşturup göstermek istiyorsak add ve remove işlemlerini kullanabilirsiniz.
 *
 *
 * Show: Görünmez olan bir fragment'ı tekrar görünür yapar. Fragment'ın lifecycle methodlarından
 * hiçbiri tetiklenmez çünkü tek yaptığı şey fragment'ı görünür yapmaktır.
 *
 * Hide: Bir fragment'ı UI'da gizler. Fragment'ın lifecycle methodlarından hiçbiri tetiklenmez
 * çünkü tek yaptığı şey fragment'ı görünmez yapmaktır.
 *
 */

class MainActivity : AppCompatActivity() {

    private lateinit var manager: FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        manager = supportFragmentManager
    }

    // Add - Remove

    fun btnAddA(view: View) {
        val fragmentA = FragmentA()
        val transaction = manager.beginTransaction()
        transaction.add(R.id.container, fragmentA, "fragmentA")
        transaction.commit()
    }

    fun btnRemoveA(view: View) {
        val fragmentA = manager.findFragmentByTag("fragmentA")
        val transaction = manager.beginTransaction()

        if (fragmentA != null) {
            transaction.remove(fragmentA)
        } else {
            Toast.makeText(this@MainActivity, "FragmentA not found.", Toast.LENGTH_SHORT).show()
        }

        transaction.commit()
    }

    fun btnAddB(view: View) {
        val fragmentB = FragmentB()
        val transaction = manager.beginTransaction()
        transaction.add(R.id.container, fragmentB, "fragmentB")
        transaction.commit()
    }

    fun btnRemoveB(view: View) {
        val fragmentB = manager.findFragmentByTag("fragmentB")
        val transaction = manager.beginTransaction()

        if (fragmentB != null) {
            transaction.remove(fragmentB)
        } else {
            Toast.makeText(this@MainActivity, "FragmentB not found.", Toast.LENGTH_SHORT).show()
        }

        transaction.commit()
    }

    // Replace

    fun btnReplaceWithFragmentA(view: View) {
        val fragmentA = FragmentA()
        val transaction = manager.beginTransaction()

        transaction.replace(R.id.container, fragmentA, "fragmentA")

        transaction.commit()
    }

    fun btnReplaceWithFragmentB(view: View) {
        val fragmentB = FragmentB()
        val transaction = manager.beginTransaction()

        transaction.replace(R.id.container, fragmentB, "fragmentB")

        transaction.commit()
    }

    // Show Hide

    fun btnHideFragmentA(view: View) {
        val fragmentA = manager.findFragmentByTag("fragmentA")
        val transaction = manager.beginTransaction()
        if (fragmentA != null) {
            transaction.hide(fragmentA)
            transaction.commit()
        } else {
            Toast.makeText(this@MainActivity, "Fragment-A not found.", Toast.LENGTH_SHORT).show()
        }
    }

    fun btnShowFragmentA(view: View) {
        val fragmentA = manager.findFragmentByTag("fragmentA")
        val transaction = manager.beginTransaction()
        if (fragmentA != null) {
            transaction.show(fragmentA)
            transaction.commit()
        } else {
            Toast.makeText(this@MainActivity, "Fragment-A not found.", Toast.LENGTH_SHORT).show()
        }
    }

    // Attach - Detach

    fun btnAttachFragmentA(view: View) {
        val fragmentA = manager.findFragmentByTag("fragmentA")
        val transaction = manager.beginTransaction()
        if (fragmentA != null) {
            transaction.attach(fragmentA)
            transaction.commit()
        } else {
            Toast.makeText(this@MainActivity, "Fragment-A not found.", Toast.LENGTH_SHORT).show()
        }
    }

    fun btnDetachFragmentA(view: View) {
        val fragmentA = manager.findFragmentByTag("fragmentA")
        val transaction = manager.beginTransaction()
        if (fragmentA != null) {
            transaction.detach(fragmentA)
            transaction.commit()
        } else {
            Toast.makeText(this@MainActivity, "Fragment-A not found.", Toast.LENGTH_SHORT).show()
        }
    }
}