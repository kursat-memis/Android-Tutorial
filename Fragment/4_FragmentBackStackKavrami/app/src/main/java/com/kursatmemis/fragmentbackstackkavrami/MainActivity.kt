package com.kursatmemis.fragmentbackstackkavrami

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentManager.OnBackStackChangedListener
import androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE

/**
 * Not: Eğerki buradan anlamazsan youtube videosunu kesin izle:
 * https://www.youtube.com/watch?v=G6KYpkQCJNM&list=PLSg_-k7KzeO8YqDZnKxTpETYn7iKhBISW&index=12
 */

/**
 * Fragment'larda BackStack Kavramı:
 * Bir Activity üzerinde ekranın üstünde A fragment'ını ekranın altında da B fragment'ını
 * açtığımızı düşünelim. Android'de back tuşunun default olarak görevi mevcut activity'i
 * sonlandırmak olduğundan dolayı biz back tuşuna bastığımızda önce B fragment'ının sonra
 * A fragment'ının ekrandan kaldırması yerine direkt mevcut activity sonlandırılacaktır.
 * Bazı durumlarda biz bunun böyle olmasını istemeyiz. Önce B fragment'ının sonra A fragment'ının
 * ekrandan kaldırılmasını bekleyebiliriz. İşte bu tür durumlar için backstack kontrolünü manuel
 * olarak kendimizin yapması gerekir.
 *
 * Biz manuel olarak backstack kavramını yönetmek için transaction'ları bir stack yapısına koyarız.
 * Not: Burada dikkat edilmesi gereken husus, fragment'ların backstack'e koyulduğu değildir.
 * Transaction'ların backstack'e koyulduğudur. Bundan dolayı biz stack'den eleman çıkardıkça
 * bir önceki fragment'a değil bir önceki transaciton'a geçiş yapmış oluruz.
 */

/**
 * BackStack'e Transaction Ekleme:
 * Transaction'u backstack'e eklemek için kullanılabilecek iki method var:
 * 1. addToBackStack(null): Stack'e transaction'ı ekler. Ancak bu transaction'a ulaşabileceğin bir
 *                          tag'e sahip olmazsın.
 * 2. addToBackStack(String tag): Stack'e belirtilen tag'i temsil eden bir transaction ekler.
 *
 * Not: Tag kullanarak stack'e transaction'ları eklemek ilerde lazım olabileceğinden dolayı
 * çok daha iyi bir seçim olacaktır.
 *
 * Bu methodları transaction.commit() methodundan önce cağırarak ilgili transaction'ı stack'e
 * ekleriz.
 */

/**
 * BackStack'den Transaction Silme:
 * Transaction'ı backstack'den silmek için kullanılabilecek iki method var:
 * popBackStack(): En üstteki transaction'ı stack'den siler.
 * popBackStack(String tag, int Flag): Flag değerinin alacağı parametrelere göre bu method farklı
 * davranış sergiler;
 * Eğer flag = 0 verilirse -> Belirtilen tag'e sahip transaction'a kadar backstack'i temizler.
 * Eğer flag = POP_BACK_STACK_INCLUSIVE verilirse = Belirtilen tag'e sahip transaction'a kadar
 *                                                  olan transactionları ve belirtilen tag'e sahip
 *                                                  olan transaction'ı stack'den siler.
 */

class MainActivity : AppCompatActivity() {

    private lateinit var manager: FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        manager = supportFragmentManager
    }

    fun btnAddA(view: View) {
        val fragmentA = FragmentA()
        val transaction = manager.beginTransaction()

        transaction.add(R.id.container, fragmentA, "fragmentA")
        transaction.addToBackStack("addFragA")
        transaction.commit()
    }

    fun btnRemoveA(view: View) {
        val fragmentA = manager.findFragmentByTag("fragmentA")
        val transaction = manager.beginTransaction()

        if (fragmentA != null) {
            transaction.remove(fragmentA)
            transaction.addToBackStack("removeFragA")
        } else {
            Toast.makeText(this@MainActivity, "FragmentA not found.", Toast.LENGTH_SHORT).show()
        }

        transaction.commit()
    }

    fun btnAddB(view: View) {
        val fragmentB = FragmentB()
        val transaction = manager.beginTransaction()
        transaction.add(R.id.container, fragmentB, "fragmentB")
        transaction.addToBackStack("addFragB")
        transaction.commit()
    }

    fun btnRemoveB(view: View) {
        val fragmentB = manager.findFragmentByTag("fragmentB")
        val transaction = manager.beginTransaction()

        if (fragmentB != null) {
            transaction.remove(fragmentB)
            transaction.addToBackStack("removeFragB")
        } else {
            Toast.makeText(this@MainActivity, "FragmentB not found.", Toast.LENGTH_SHORT).show()
        }

        transaction.commit()
    }

    fun btnReplaceWithFragmentA(view: View) {
        val fragmentA = FragmentA()
        val transaction = manager.beginTransaction()

        transaction.replace(R.id.container, fragmentA, "fragmentA")
        transaction.addToBackStack("replaceFragA")
        transaction.commit()
    }

    fun btnReplaceWithFragmentB(view: View) {
        val fragmentB = FragmentB()
        val transaction = manager.beginTransaction()

        transaction.replace(R.id.container, fragmentB, "fragmentB")
        transaction.addToBackStack("replaceFragB")
        transaction.commit()
    }

    fun btnHideFragmentA(view: View) {
        val fragmentA = manager.findFragmentByTag("fragmentA")
        val transaction = manager.beginTransaction()
        if (fragmentA != null) {
            transaction.hide(fragmentA)
            transaction.addToBackStack("hideFragA")
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
            transaction.addToBackStack("showFragA")
            transaction.commit()
        } else {
            Toast.makeText(this@MainActivity, "Fragment-A not found.", Toast.LENGTH_SHORT).show()
        }
    }

    fun btnAttachFragmentA(view: View) {
        val fragmentA = manager.findFragmentByTag("fragmentA")
        val transaction = manager.beginTransaction()
        if (fragmentA != null) {
            transaction.attach(fragmentA)
            transaction.addToBackStack("attachFragA")
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
            transaction.addToBackStack("detachFragA")
            transaction.commit()
        } else {
            Toast.makeText(this@MainActivity, "Fragment-A not found.", Toast.LENGTH_SHORT).show()
        }
    }

    fun btnPopBackStack1(view: View) {
        manager.popBackStack()
    }

    fun btnPopBackStack2(view: View) {
        manager.popBackStack("addFragA", 0)
    }

    fun btnPopBackStack3(view: View) {
        manager.popBackStack("addFragA", POP_BACK_STACK_INCLUSIVE)
    }

}