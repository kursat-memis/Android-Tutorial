package com.kursatmemis.sharedpreferences

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

/**
 * SharedPreferences Nedir?
 * Küçük çaplı verileri key-value çifti şeklinde bir XML dosyasına kaydeden küçük çaplı bir depolama
 * mekanizmasıdır.
 * Local'de (uygulamanın tutulduğu cihazda) depolama işlemi yapar.
 * Verilerimizi SQLite - Room gibi database'ler kullanarakta depolayabiliriz ancak küçük dataları
 * tutmak için database kullanmak zahmetlidir ve SharedPref. kullanmak küçük datalar için çok daha
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
 * MODE_WORLD_READABLE: Bu mod, diğer uygulamaların SharedPreferences'ın verileri tutmak için
 * oluşturduğu XML dosyasını okumasına izin verir,
 * Ancak yazma işlemi yapmalarına izin vermez. Bu mod, eski Android sürümlerinde kullanıma sunulmuştu,
 * ancak güvenlik nedenleriyle tavsiye edilmez ve artık kullanımdan kaldırılmıştır.
 * Bu nedenle, bu modu kullanmaktan kaçınmalısınız.
 *
 * MODE_WORLD_WRITEABLE: Bu mod, diğer uygulamaların SharedPreferences'ın verileri tutmak için
 * oluşturduğu XML dosyasına yazma işlemi yapmasına izin verir.
 * Ancak, güvenlik açısından riskli olduğu için kullanımdan kaldırılmıştır
 * ve artık kullanılmamalıdır.
 */

/**
 * Methodlar:
 * edit(): SharedPreferences'da veri kaydetmek için bir 'Editor' objesine ihtiyacımız var ve bu method
 * bize bir 'Editor' objesi return ediyor.
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
 * Nasıl Kullanılır?    :
 * 1. Öncelikle SharedPreferance objesi elde etmeliyiz. Bu objeyi bize return eden iki methodumuz
 * var:
 *    1. getPreferances(mode: Int): Bu method ile bir SharedPref oluşturduğumuzda, verilerin
 *    depolanması için mevcut activity adında bir XML dosyası oluşturulur ve kullanılır.
 *
 *    2. getSharedPreferences(name: String, mode: Int): Bu method ile bir SharedPref
 *    oluşturduğumuzda, verilerin depolanması için kullanılacak olan XML dosyasının adını
 *    'name' parametresinde belirleyebiliyoruz. [Genelde Package adı vermemiz önerilir.]
 *
 * 2. Eğer SharedPref'e bir veri kaydedeceksek bir 'Editor' objesi elde etmeliyiz. Bunu elde etmek
 *    içinde oluşturduğumuz sharedPref objesi üzerinden edit() methodunu çağırıyoruz.
 *    [Not: Eğer sadece veri okumak istiyorsak 'Editor' objesine ihtiyacımız yok.]
 *
 * 3. Son olarak eğer SharedPref'e veri kaydetmiş isek 'Editor' objesi üzerinden commit() veya
 *    apply() methodunu çağırmayı unutmuyoruz.
 *
 *    Not: Eğer kaydetme işleminin başarılı-başarısız olduğu bilgisini elde etmek istiyorsan
 *    'commit()' kullan. Ancak bu bilgi senin için önemli değilse 'apply()' kullan. Çünkü 'apply()'
 *    methodu asenkron olarak kaydetme işlemini yürüttüğünden dolayı MainThread'in diğer işlevleri
 *    yerine getirmesi ve kullanıcı etkileşimlerine yanıt vermesi engellenmiyor. Ancak 'commit()'
 *    methodu senkron olarak çalıştığından dolayı bu method çalışmasını bitirene kadar MainThread'i
 *    bloke etmek durumunda kalıyor. Bundan dolayı 'apply()' methodu daha performanslıdır.
 *
 *    Not: Eğer illa ki commit() methodunu kullanmak istersen bu methodu farklı bir Thread üzerinde
 *    veya Coroutine ile birlikte kullanmak performansı daha olumlu etkiler.
 *
 *    Not: apply() yöntemi için Android diyor ki: Bu methodu kullandığınızda Activity-Fragment'ın
 *    lifecycle'ı hakkında endişe etmeyin. Bunların durumları değişse sizin kaydetmek istediğiniz
 *    veriler kaydedilecektir. Yani; eğer apply methodu çalışmasını tamamlamadan önce Activity
 *    destroy edilirse, yine de kaydetmek istediğimiz veriler başarıyla kaydedilmiş olacaktır.
 */

class MainActivity : AppCompatActivity() {

    private lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPref = getSharedPreferences("myPref", MODE_PRIVATE)
        val editor = sharedPref.edit()

        saveData(editor)
        readData()
    }

    private fun saveData(editor: SharedPreferences.Editor) {
        editor.putInt("age", 18)
        editor.putString("name", "Ali")
        editor.putBoolean("isStudent", true)
        editor.commit() // Senkron olarak verileri kaydeder ve geriye true-false return eder.
        // editor.apply() // Asenkron olarak verileri kaydeder ve geriye bir değer return etmez.

        /**
         * Not: Eğer illa ki commit() methodunu kullanmak istersen bu methodu farklı bir Thread
         * üzerinde veya Coroutine ile birlikte kullanmak performansı daha olumlu etkiler.
         */
    }

    private fun readData() {
        val age = sharedPref.getInt("age", -1)
        val name = sharedPref.getString("name", "name not found")
        val isStudent = sharedPref.getBoolean("isStudent", false)

        Log.w("mKm - krst", "Age: $age, Name: $name, isStudent: $isStudent")
    }
}