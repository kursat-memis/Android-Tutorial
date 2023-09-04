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
 * Internette ekranda dialog göstermek onPause methodunu tetikler yazmıslar ama
 * aşağıdaki kodda ekranda fragment göstermek ve dialog göstermek herhangi bir lifecycle
 * methodunu tetiklemedi. Bu belki eskiden dedikleri gibiydi veya burada kastedilen dialog penceresi
 * sistemin bize gösterdiği dialog penceresi olabilir. (Örn: Şarj bittiğinde ekrana gelen pencere.)
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
 */

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

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

    }

    // Ekran yan çevrildiğinde bu method calisir. Bu method içinde verileri kaydedebiliriz.
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.w("mKm - lifecycle", "onSaveInstanceState")
        outState.putString("value", "100")
    }

    // Ekran yan çevrildikten sonra uygulama tekrar ayağa kalkarken bu method calisir.
    // Bu method içinde kaydettiğimiz verileri alabiliriz.
    // Alternatif olarak bu method yerine onCreate methodu içindeki bundle objesinide
    // kullanarak kaydedilen verileri alabiliriz.
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val value = savedInstanceState.getString("value").toString()
        Log.w("mKm - lifecycle", "onRestoreInstanceState - value: $value")
    }

    override fun onStart() {
        super.onStart()
        Log.w("mKm - lifecycle", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.w("mKm - lifecycle", "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.w("mKm - lifecycle", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.w("mKm - lifecycle", "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.w("mKm - lifecycle", "onDestroy")
    }

    override fun onRestart() {
        super.onRestart()
        Log.w("mKm - lifecycle", "onRestart")
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