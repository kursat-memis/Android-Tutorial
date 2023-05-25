package com.kursatmemis.popupmenu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.MenuInflater
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu

/**
 * PopUp menu bir widget'a tıkladığımızda bize menu elemanlarını gösteren bir menü çeşitidir.
 * Widget: Araçlardır. EditText, Button, ListView, TextView vb.
 * Popup menu bir widget'a bağlı olmak zorundadır. Hiçbir şeyden bağımsız olarak ekranda gösterile-
 * mez.
 *
 * Nasıl Oluşturulur?
1. Res klasörünün altında bir menu klasörü oluşturulur. (Bu klasörün adı 'menu' olmalıdır.)
2. Oluşturulan menu klasörünün içinde 'Menu Recource File' oluşturulur.
3. Menu tagleri içinde item'lar tanımlanır ve bunlara id verilir.
(Ayrıca item'lar içinede menu atılabilir.)
4. PopupMenu class'inden bir obje oluşturulur.
5. MenuInflater objesi oluşturulur.
6. Menu inflate edilir ve show methodu cagilarak ekranda gösterilir.
7. onMenuItemClick methodu ile menunun üzerinde tıklanan itemlar üzerinden işlem yapılabilir.
8. Oluşturulan popup menu objesi üzerinden show methodu cagrili ve menu ekranda gösterilir.

PopupMenu Constructor'ındaki Parametreler:
1. context: Context nesnesi.
2. anchor: Popup menünün hangi view öğesine bağlı olduğunu gösteren View nesnesi.
(Popup menuler bir view'a bağlı olmak zorundadır. Bu, hangi view üzerinde işlem
yaptığımızda menunun gösterileceğini belirler.)
3. gravity: Popup menu'nun bağlı olduğu view'a göre hangi yönde görüntüleneceğini belirler.
Alacağı değer, Gravity sınıfındaki sabitlerden biri olmalıdır. Örneğin, Gravity.RIGHT veya
Gravity.LEFT.

MenuInflater.inflate Methodunun Parametreleri:
1. menuRes: PoupMenu'nun tanımlandığı xml dosyasının id'sidir.
2. menu: Inflate edilen menü item'larının hangi menu objesine ekleneceğini belirleyen
menü objesidir.
 */

/*
// İkinci Yöntem:

class MainActivity : AppCompatActivity(), PopupMenu.OnMenuItemClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    // XML üzerinden buton onclick attribute'u showPopUp methoduna baglandi.
    fun showPopup(view: View){
        val popupMenu=PopupMenu(this,view)
        val inflater:MenuInflater=popupMenu.menuInflater
        inflater.inflate(R.menu.popup_menu,popupMenu.menu)

        /*
            Bizim sınıfımız, setInMenuItemClickListener methodunda parametre olarak beklenen
            PopupMenu.OnMenuItemClickListener interface'ini implement ettiğinden dolayı
            bu methoda, this keyworduyle kendi sınıfımızın objesini gönderdik. Ver artık menü
            üzerindeki bir item'a tıklanıldığında bu interface içindeki
            onMenuItemClick methodu calisacaktir.

         */
        popupMenu.setOnMenuItemClickListener(this@MainActivity)
        popupMenu.show()
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        Toast.makeText(this@MainActivity, "sadsadsad", Toast.LENGTH_SHORT).show()
        when (item?.itemId) {
            R.id.action_profile -> {
                Toast.makeText(this@MainActivity, "Profile", Toast.LENGTH_SHORT)
                    .show()
                return true
            }
            R.id.action_settings -> {
                Toast.makeText(this@MainActivity, "Settings", Toast.LENGTH_SHORT)
                    .show()
                return true
            }

            R.id.action_logout -> {
                Toast.makeText(this@MainActivity, "Logout", Toast.LENGTH_SHORT)
                    .show()
                return true
            }
            else -> return false
        }
    }
}*/

class MainActivity : AppCompatActivity() {
    private lateinit var showPopupMenuButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        showPopupMenuButton = findViewById(R.id.showPopupMenuButton)

        showPopupMenuButton.setOnClickListener {
            val popupMenu: PopupMenu = PopupMenu(
                this@MainActivity, it, Gravity.NO_GRAVITY,
                0, android.R.style.Widget_Holo_Light_PopupMenu
            )

            val menuInflater: MenuInflater = getMenuInflater()
            menuInflater.inflate(R.menu.popup_menu, popupMenu.menu)

            /*
                Bir menü öğesi seçildiğinde, gerçekleştirilmesini istediğiniz işlemi belirleyip,
                işlem başarıyla tamamlandıysa true değerini döndürmeniz gerekiyor.

                Eğer menü öğesi seçildiğinde, gerçekleştirilmesini istediğiniz işlem başarısız olursa
                veya işlem için belirlediğiniz koşullar sağlanmazsa, false değerini döndürmelisiniz.

                it parametresi, tıklanan menu item'ını temsil eder. Bu item'ın id'si üzerinden
                işlemleri kontrol edebiliriz.
             */

            popupMenu.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.action_profile -> {
                        Toast.makeText(this@MainActivity, "Profile", Toast.LENGTH_SHORT)
                            .show()
                        true
                    }
                    R.id.action_settings -> {
                        Toast.makeText(this@MainActivity, "Settings", Toast.LENGTH_SHORT)
                            .show()
                        true
                    }

                    R.id.action_logout -> {
                        Toast.makeText(this@MainActivity, "Logout", Toast.LENGTH_SHORT)
                            .show()
                        true
                    }
                    R.id.action_logoutFacebook -> {
                        Toast.makeText(this@MainActivity, "Logout Facebook", Toast.LENGTH_SHORT)
                            .show()
                        true
                    }
                    R.id.action_logoutInstagram -> {
                        Toast.makeText(this@MainActivity, "Logout Instagram", Toast.LENGTH_SHORT)
                            .show()
                        true
                    }
                    else -> false
                }
            }

            popupMenu.show()
        }
    }
}
