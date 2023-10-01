package com.kursatmemis.android_life_cycle

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.kursatmemis.android_life_cycle.databinding.ActivityMainBinding

/**
 * Okunması tavsiye edilen kaynak:
 * https://medium.com/@betul.sandikci/android-aktivite-ya%C5%9Fam-d%C3%B6ng%C3%BCs%C3%BC-activity-life-cycle-bir-aktivite-olmak-i-e9b7c88ec67c
 */

/**
 * Activity Life Cycle Temelde 4 Evreye Ayrılır:
 * 1. Running: Activity görünürdür ve kullanıcıyla etkileşim halindedir.
 * 2. Paused: Activity kısmen görünür haldedir. Eğer activity paused halindeyken cihazın memory'si
 * oldukça düşerse sistem tarafından activity sonlandırılabilir.
 * Pause Durumuna Örnek: Uygulama açıkken sistem tarafından bir dialog penceresi gözükmesi.
 * (Pil azalması vb.)
 * 3. Stopped: Activity görünür halde değildir ancak yine de arka planda çalışır durumdadır.
 * Sistem ek memory'e ihtiyaç duyarsa stopped durumundaki activity'i sonlandırabilir.
 * 4. Killed: Activity'nin calismasi tamamen sonlandırılır.
 * (finish methodu çağrıldığında da activity killed durumuna geçer.)
 */

/**
 * Sistem tarafından ekrana bir dialog penceresi geldiğinde (örn: şarj uyarısı) onPause() methodu
 * calisir ve dialog kapatıldığında onResume() methodu calisir.
 * Ancak Activity içinde kendi oluşturduğumuz dialog pencereleri life cycle methodlarını
 * tetiklemiyor.
 */

/**
 * Kombinasyonlar:
 * 1. Uygulama ilk açılırken ve destroy edilen activity tekrar açılırken:
 * onCreate - onStart - onResume
 *
 * 2. Uygulama destroy edilmeden başka bir uygulamaya geçildiğinde;
 * onPause - onStop
 *
 * 3. Destroy edilmeyen uygulama tekrar açılırken;
 * onRestart - onStart - onResume
 *
 * 4. Uygulama destroy edilerek başka bir uygulamaya geçildiğinde;
 * onPause - onStop - onDestroy
 *
 * 5. Destroy edilen uygulama tekrar açılırken;
 * onCreate - onStart - onResume
 */

/**
 * Örnek Senaryolar:
 *
 * Activity oluşturulduğunda;
 * onCreate - onStart - onResume
 *
 * Back Tuşuna Basıldığında:
 * onPause - onStop - onDestroy
 *
 * Back Tuşuna Basıldıktan Sonra Tekrar Activity'i Açtığımızda:
 * onCreate - onStart - onResume
 *
 * Home Tuşuna Basıldığında:
 * onPause - onStop
 *
 * Home Tuşuna Bastıktan Sonra Tekrar Uygulamayı Açtığımızda:
 * onRestart - onStart - onResume
 *
 * Üst Bildirim Ekranından Başka Bir Uygulamaya Geçiş Yaptığımızda:
 * onPause - onStop
 *
 * Üst Bildirim Ekranından Başka Uygulamaya Geçtikten Sonra Geri Döndüğümüzde:
 * onRestart - onStart - onResume
 *
 * Telefon Ekranını Kapattığımızda:
 * onPause - onStop
 *
 * Ekranı Tekrar Açtığımızda:
 * onRestart - onStart - onResume
 *
 * Telefonu Yatay Olarak Döndürdüğümüzde:
 * onPause – onStop – onSaveInstanceState – onDestroy - onCreate – onStart – onRestoreIntanceState – onResume
 *
 * Başka Bir Activity'e Geçildiğinde:
 * onPause(MainAct.) - onCreate(Activity-2) - onStart(Activity-2) - onResume(Activity-2) - onStop(MainAct.)
 * Not: Eğer MainActivity'den ActivityB'ye geçiş yaparken finish methodu çağrılmışsa, MainActivity'nin onDestroy methoduna
 * calisir.
 *
 * Gidilen Activity'den Back Tuşu İle Geri Dönüldüğünde;
 * onPause(Activity-2) - onRestart(MainAct.) - onStart(MainAct.) - onResume(MainAct.) - onStop(Activity-2) - onDestroy(Activity-2)
 *
 * Eğer activity çalışır durumdayken sistem tarafından bir dialog penceresi(pil uyarısı vb.) gözükürse:
 * onPuase()
 *
 * Sistemin gönderdiği dialog penceresi kapatılırsa:
 * onResume()
 */

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    /**
     * Bir activity başlatıldığında ilk çalışan method'dur.
     * Uygulama açıldığında yalnızca bir kez gerçekleşmesini istediğimiz işlemleri bu method'da
     * yaparız.
     * Örn:
     * - Ekranda gösterilecek olan layout'un setContentView() ile belirtilmesi.
     * - View'ların başlatılması.
     * - Listener'ların eklenmesi.
     * - Database işlemleri.
     * - Üçüncü parti kütüphanelerin başlatılması.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.w("mKm - lifecycle", "onCreate")

        binding.showAlertDialogButton.setOnClickListener {
            showAlertDialog()
        }

        binding.showFragmentButton.setOnClickListener {
            showFragment()
        }

        binding.showActivityAsDialogButton.setOnClickListener {
            showDialogAsActivity()
        }

        binding.switchActivityButton.setOnClickListener {
            val intent = Intent(this@MainActivity, ActivityB::class.java)
            startActivity(intent)
        }

        binding.showNameButton.setOnClickListener {
            val name = binding.nameEditText.text.toString()
            binding.nameTextView.text = name
        }

    }

    /**
     * Bu methodlar birlikte activity kullanıcıya görünür hale gelir.
     * onStop() methodu çalıştığında bazı kaynakları serbest bıraktığımız durumlar olabilir.
     * Bu durumlarda, uygulamayı tekrar açtığımızda kaynakları geri almak için onStart() veya
     * onRestart() methodları kullanılabilir.
     */
    override fun onStart() {
        super.onStart()
        Log.w("mKm - lifecycle", "onStart")
    }

    /**
     * Sistem bir dialog penceresi (pil yüzdesi vb.) göstererek onPause() methodu çalıştığında
     * bazı kaynakları serbest bıraktığımız durumlar olabilir.
     * Bu durumlarda, dialog penceresini kapattığımız zaman bu kaynakları onResume() methodunda
     * geri yükleyebiliriz.
     */
    override fun onResume() {
        super.onResume()
        Log.w("mKm - lifecycle", "onResume")
    }

    /**
     * Sistem tarafından bir dialog penceresi (pil yüzdesi vb.) gösterildiğinde çalışan method'dur.
     * Bu durumlarda bazı kaynakları serbest bırakmak isteyebiliriz. Bu işlemlerin gerçekleşmesi
     * için gerekli kodları bu method içinde yazarız.
     */
    override fun onPause() {
        super.onPause()
        Log.w("mKm - lifecycle", "onPause")
    }

    /**
     * Aktivite görünür olmadığında çalışan method'dur. Bu method'da ağır kaynakları serbest
     * bırakmak veya gerekli olmayan işlemleri durdurmak için gerekli kodlamaları yaparız.
     */
    override fun onStop() {
        super.onStop()
        Log.w("mKm - lifecycle", "onStop")
    }

    /**
     * Aktivite tamamen yok edildiğinde çalışacak method'dur.
     */
    override fun onDestroy() {
        super.onDestroy()
        Log.w("mKm - lifecycle", "onDestroy")
    }

    /**
     * onStop() durumunda olan bir aktivitenin yeniden ayağa kalkarken çalışacak olan method'dur.
     * Serbest bırakılmış kaynakların geri alınması veya aktivitenin tekrar çalıştırıldığında
     * yapılmasını istediğimiz işlemler bu method içinde yapılır.
     */
    override fun onRestart() {
        super.onRestart()
        Log.w("mKm - lifecycle", "onRestart")
    }

    /**
     * Ekran yan çevrildiğinde vs. veri kaydetmek istiyorsak bu method içinde kaydedebiliriz.
     */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.w("mKm - lifecycle", "onSaveInstanceState")
        val name = binding.nameTextView.text.toString()
        outState.putString("name", name)
    }

    /**
     * Ekran yan çevrildikten sonra vs. uygulama tekrar ayağa kalkarken bu method calisir.
     * Bu method içinde kaydettiğimiz verileri alabiliriz.
     * Alternatif olarak bu method yerine onCreate methodu içindeki bundle objesinide
     * kullanarak kaydedilen verileri alabiliriz.
     */
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.w("mKm - lifecycle", "onRestoreInstanceState")
        val name = savedInstanceState.getString("name").toString()
        binding.nameTextView.text = name
    }








    private fun showFragment() {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val myFragment = MyFragment()
        fragmentTransaction.replace(R.id.frameLayout, myFragment)
        fragmentTransaction.commit()
    }

    private fun showDialogAsActivity() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.activity_dialog)
        dialog.show()
    }

    private fun showAlertDialog() {
        val builder = AlertDialog.Builder(this@MainActivity)
        builder.setTitle("Title")
        builder.setMessage("Message")
        builder.show()
    }
}