package com.kursatmemis.contextmenu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast

/**
 * Context menu bir widget'a UZUN bastığımızda bize menu elemanlarını gösteren bir menü çeşitidir.
 * Widget: Araçlardır. EditText, Button, ListView, TextView vb.
 *
 * Nasıl Oluşturulur?
1. Res klasörünün altında bir menu klasörü oluşturulur. (Bu klasörün adı 'menu' olmalıdır.)
2. Oluşturulan menu klasörünün içinde 'Menu Recource File' oluşturulur.
3. Menu tagleri içinde item'lar tanımlanır ve bunlara id verilir.
(Ayrıca item'lar içinede menu atılabilir.)
4. MainActivity class'ı içinde onCreateContextMenu methodunu override ediyoruz.
5. onCreateContextMenu methodu içerisinde MenuInflater objesi oluşturulur.
6. Menu inflate edilir.
7. onContextItemSelected methodu MainActivity içerisinde override edilir ve menunun üzerinde
tıklanan itemlar üzerinden işlem yapılabilir.
8. onCreate methodu içinde, registerForContextMenu() methoduna, context menunun hangi view'da
gözükmesini istiyorsan o view'ın objesini argüman olarak yolla.

MenuInflater.inflate Methodunun Parametreleri:
1. menuRes: ContextMenu'nun tanımlandığı xml dosyasının id'sidir.
2. menu: Inflate edilen menü item'larının hangi menu objesine ekleneceğini belirleyen
menü objesidir. (onCreateContextMenu parametresindeki menu'yu argüman olarak yolla.)
 */

// ContextMenu'nun ListView üzerinde gözükmesi.

/*
class MainActivity : AppCompatActivity() {
    private lateinit var listView: ListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.context_menu_list_view)
        listView = findViewById(R.id.listView)

        var arrayForArrAdapter = arrayOf("One", "Two", "Three",
            "Four", "Five", "Six")

        var adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayForArrAdapter)
        listView.adapter = adapter

        registerForContextMenu(listView)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        val menuInflater: MenuInflater = menuInflater
        menuInflater.inflate(R.menu.context_menu_listview, menu)

    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_item_1 -> {
                Toast.makeText(
                    this@MainActivity, "Item - 1",
                    Toast.LENGTH_SHORT
                ).show()
                return true
            }
            R.id.action_item_2 -> {
                Toast.makeText(
                    this@MainActivity, "Item - 2",
                    Toast.LENGTH_SHORT
                ).show()
                return true
            }
        }
        return false
    }
}*/


class MainActivity : AppCompatActivity() {
    private lateinit var showContextMenuTextView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        showContextMenuTextView = findViewById(R.id.showContextMenuTextView)

        registerForContextMenu(showContextMenuTextView)

    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        val menuInflater: MenuInflater = getMenuInflater()
        menuInflater.inflate(R.menu.context_menu, menu)
        menu?.setHeaderTitle("Context Menu") // Menuye bir header(başlık) ekler.

    }
    /*
    Bir menü öğesi seçildiğinde, gerçekleştirilmesini istediğiniz işlemi belirleyip,
    işlem başarıyla tamamlandıysa true değerini döndürmeniz gerekiyor.

    Eğer menü öğesi seçildiğinde, gerçekleştirilmesini istediğiniz işlem başarısız olursa
    veya işlem için belirlediğiniz koşullar sağlanmazsa, false değerini döndürmelisiniz.

    it parametresi, tıklanan menu item'ını temsil eder. Bu item'ın id'si üzerinden
    işlemleri kontrol edebiliriz.
    */
    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.profile -> {
                Toast.makeText(this@MainActivity, "Profile", Toast.LENGTH_SHORT)
                    .show()
                return true
            }
            R.id.settings -> {
                Toast.makeText(this@MainActivity, "Settings", Toast.LENGTH_SHORT)
                    .show()
                return true
            }
            R.id.logout -> {
                Toast.makeText(this@MainActivity, "Logout", Toast.LENGTH_SHORT)
                    .show()
                return true
            }
            R.id.action_logoutFacebook -> {
                Toast.makeText(this@MainActivity, "Logout Facebook", Toast.LENGTH_SHORT)
                    .show()
                return true
            }
            R.id.action_logoutInstagram -> {
                Toast.makeText(this@MainActivity, "Logout Instagram", Toast.LENGTH_SHORT)
                    .show()
                return true
            }
        }
        return false
    }
}


