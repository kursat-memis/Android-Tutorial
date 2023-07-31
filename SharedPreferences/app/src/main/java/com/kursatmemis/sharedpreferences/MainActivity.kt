package com.kursatmemis.sharedpreferences

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.kursatmemis.sharedpreferences.managers.SharedPrefManager

/**
 * SharedPreferences Nedir?
 * Verileri key/value şeklinde depolamak ve bunları okumak için kullandığımız bir yapıdır.
 * Local'de (uygulamanın tutulduğu cihazda) depolama işlemi yapar.
 * Verilerimizi SQLite gibi database'ler kullanarakta depolayabiliriz ancak küçük dataları tutmak
 * için database kullanmak zahmetlidir ve sharedpreference kullanmak küçük datalar için çok daha
 * pratiktir.
 *
 * ShraredPref ile;
 * String,Int,Boolean,Float,Long tipinde verileri depolayabiliriz.
 */

/**
 * SharedPreferences Modları:
 *
 * Not: MODE_PRIVATE dışındaki modlar kaldırıldı.
 *
 * MODE_PRIVATE: Bu mod, oluşturulan SharedPreferences dosyasının sadece uygulama içinden
 * erişilebilir olacağını belirtir. Diğer uygulamalar bu verilere erişemez. Bu mod, genellikle
 * uygulamanızın kendi özel verilerini saklamak için kullanılır ve varsayılan moddur.
 *
 * MODE_APPEND: Normalde, SharedPreferences nesnesini oluştururken Context.MODE_PRIVATE
 * kullanıldığında, önceki veriler silinir ve yeni veriler ile değiştirilir.
 * Ancak, Context.MODE_APPEND kullanarak SharedPreferences nesnesini oluşturduğunuzda,
 * mevcut verilere ek olarak yeni verileri ekleyebilirsiniz. Yani, önceki veriler korunur ve
 * yeni veriler bu verilere eklenir.
 *
 * MODE_WORLD_READABLE: Bu mod, diğer uygulamaların SharedPreferences dosyasını okumasına izin verir,
 * ancak yazma işlemi yapmalarına izin vermez. Bu mod, eski Android sürümlerinde kullanıma sunulmuştu,
 * ancak güvenlik nedenleriyle tavsiye edilmez ve artık kullanımdan kaldırılmıştır.
 * Bu nedenle, bu modu kullanmaktan kaçınmalısınız.
 *
 * MODE_WORLD_WRITEABLE: Bu mod, diğer uygulamaların SharedPreferences dosyasına yazma işlemi
 * yapmasına izin verir. Ancak, güvenlik açısından riskli olduğu için kullanımdan kaldırılmıştır
 * ve artık kullanılmamalıdır.
 */

/**
 * Methodlar:
 * edit(): SharedPreferenceste değişiklikler yapabilmek ve bu değişiklikleri okuyabilmek için bir
 * editör nesnesi almalıyız.
 * put(): Veriyi kaydetmek için kullanılır.
 * getAll(): Tüm değerleri almak için kullanılır.
 * getBoolean(String key, boolean defValue): Bool değer almak için kullanılır.
 * getFloat(String key, float defValue): Float değer almak için kullanılır.
 * getInt(String key, int defValue): Int değer almak için kullanılır.
 * getLong(String key, long defValue): Long değer almak için kullanılır.
 * getString(String key, String defValue): String değer almak için kullanılır.
 * commit(): Editor’daki verileri dosyaya yazar (synchronously olarak). Geriye true - false return eder.
 * apply(): Editor’daki verileri dosyaya yazar (asynchronously olarak). Geriye bir değer return etmez.
 * clear(): Tüm verileri siler.
 * remove(): Anahtar değeri girilen veriyi siler.
 */

/**
 * Kullanımı:
 * Bir manager class'ı oluşturalım ve bu class üzerinde gerekli işlemlerimizi yapalım.
 * (Manager class'ı içinde ilgili kodlar açıklandı.)
 */

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPref = SharedPrefManager()
        sharedPref.setSharedPreference(this, "name", "John")
        sharedPref.setSharedPreference(this, "age", 25)

        val name = sharedPref.getSharedPreference(this, "name", "")
        val age = sharedPref.getSharedPreference(this, "age", -1)
        if (name.isNotEmpty() && age != -1) {
            Log.w("mKm - sharedpref", "Name: $name")
            Log.w("mKm - sharedpref", "Age: $age")
        }

        sharedPref.getAll(this)

    }
}