package com.kursatmemis.optionmenu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast

/*
 * Option Menu Nedir?
 * Action bar'ın sağ tarafında, üç tane üst üste 3 nokta ile bize gösterilen menu çeşitidir.

* Nasıl Oluşturulur?
1. Res klasörünün altında bir menu klasörü oluşturulur. (Bu klasörün adı 'menu' olmalıdır.)
2. Oluşturulan menu klasörünün içinde 'Menu Recource File' oluşturulur.
3. Menu tagleri içinde item'lar tanımlanır ve bunlara id verilir.
* (Ayrıca item'lar içinede menu atılabilir.)
4. MainActivity class'ı içinde, onCreateOptionsMenu methodu override edilir.
5. onCreateOptionsMenu methodu içerisinde MenuInflater objesi oluşturulur.
6. Menu inflate edilir.
7. MainActivity class'ı içinde onOptionsItemSelected methodu override edilerek menu üzerindeki
   tıklanan itemlar üzerinden işlem yapılabilir.

MenuInflater.inflate Methodunun Parametreleri:
1. menuRes: PoupMenu'nun tanımlandığı xml dosyasının id'sidir.
2. menu: Inflate edilen menü item'larının hangi menu objesine ekleneceğini belirleyen
menü objesidir. (onCreateOptionsMenu parametresindeki menu argüman olarak gönderilir.)

 * XML dosyasında, menu item'larına showAsAction adında bir attribute verilebilir.
 * app:showAsAction=”never”:Item action bar ‘ın altında yer alır.
 * app:showAsAction=”always”:Her zaman action bar da yer alır-yer olup olmadığını kontrol
   etmeksizin-Tasarım çakışması yaşanabilir.
 * app:showAsAction=”ifRoom”:Yer olup olmadığın kontrol eder ve yer varsa
   item action bar’da yer alır.
 * app:showAsAction=”withText”:Item action bar’da text olarak gözükür.
*/

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater: MenuInflater = getMenuInflater()
        menuInflater.inflate(R.menu.option_menu, menu)
        return true
    }

    /*
    Bir menü öğesi seçildiğinde, gerçekleştirilmesini istediğiniz işlemi belirleyip,
    işlem başarıyla tamamlandıysa true değerini döndürmeniz gerekiyor.

    Eğer menü öğesi seçildiğinde, gerçekleştirilmesini istediğiniz işlem başarısız olursa
    veya işlem için belirlediğiniz koşullar sağlanmazsa, false değerini döndürmelisiniz.

    it parametresi, tıklanan menu item'ını temsil eder. Bu item'ın id'si üzerinden
    işlemleri kontrol edebiliriz.
    */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
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