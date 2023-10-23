package com.kursatmemis.databinding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.kursatmemis.databinding.databinding.ActivityMainBinding

/**
 * Not: Xml'e bağlanan objelerin değerleri değiştikçe ekrandaki view'larda da değerlerin değişmesini
 * istiyorsak ObservableField objelerini kullanarak bunu sağlayabiliriz.
 */

/**
 * DataBinding Nasıl Kullanılır?
 * 1. build.gradle(module) dosyasında android scope'u içine aşağıdaki kod yazılır:
    buildFeatures {
        dataBinding = true
    }
 * 2. XML dosyasına gidilerek oradaki view'lar, <layout> </layout> taglerinin arasına alınır.
 * Ardından root element'deki namespace'ler(xmlns ile başlayan attribute'ler), layout tag'inin
 * attribute'u olarak taşınır.
 * (Bunu daha pratik yapmak için; xml'de boş bir alana tıklayıp alt + enter yaptığımızda ortaya
 * çıkan 'convert to data binding layout' seçeneğine tıklayarak otomatik olarak bu işlemlerin
 * yapılmasını sağlayabiliriz.)
 * 3. Activity'de bir binding objesi tanımlanır.
 * (xml dosyasının adına göre; activity_main.xml ----> ActivityMainBinding)
 * 4. Binding objesine değer atanarak bu obje başlatılır. Ardından bu objeyi, viewBinding'de
 * olduğu gibi view'lara erişmek için kullanarabiliriz.
 */

/**
 * XML Dosyasında Data Bağlama:
 * 1. XML dosyasında <data> </data> tagleri içinde, <variable /> tag'i kullanılarak bir değişken
 * tanımlanır. <variable/> tagindeki 'name' attribute'u ile değişkenin adı, 'type' attribute'u ile
 * değişkenin tipi belirlenir.
 * 2. Bu tanımlanan değişken(data), hangi view'da kullanılacaksa o view'a gidilir ve view'ın ilgili
 * attribute'une '@{}' ifadesi kullanılarak atanır.
 * 3. onCreate() methodunda binding objesi kullanılarak xml'de tanımlanan variable'ın değer ataması
 * yapılır.
 */

/**
 * View'a Method Bağlama:
 * XML içinde methodun bulunduğu class tipinde bir variable tanımlanır(örn: toastMessageDisplayer).
 * Ardından kotlin kodu ile bu değişkene değer atanır.
 * (binding.toastMessageDisplayer = ToastMessageDisplayer())
 * Daha sonrasında butonun onClick attribute'une bu method atanır.
 * (android:onClick="@{toastMessageDisplayer::showToast}")
 *
 * Not: Aslında dataBinding kullanmadanda bu işlem yapılabilirdi. Mesela MainActivity içinde
 * tanımlanmış olan myOnClick(view: View) adında bir methodumuz olsun varsayalım.
 * XML dosyasında button'un onClick attribute'une direkt methodun adı verilip (onClick = myOnClick),
 * butona tıklanıldığında bu methodun çalışması sağlanabilirdi.
 * Ancak burada myOnClick methodunun MainActivity class'ı içinde tanımlanmış olması gerekirdi.
 * Eğer ki biz tasarımsal kodlamalarımızı MainActivity içinde tutmak istemiyorsak,
 * burada butona tıklanıldığında çalışacak olan methodu başka bir class'da
 * tanımlar ve dataBinding sayesinde bu başka class'daki methodu butonun onClick'ine bağlayabiliriz.
 *
 * Ayrıca bu iki bağlama tekniği arasındaki önemli farklardan birisi ise şudur;
 * DataBinding kullanarak bir view'a method bağladığımızda eğer ki böyle bir method yok ise
 * compiler time'da hata alırız. Hatta uygulamayı çalıştırmadan daha kodu yazarken editör böyle
 * bir method'un olmadığı konusunda bize uyarı verir.
 * Ancak diğer türlü dataBinding kullanmadan direkt method adını verdiğimiz zaman, böyle bir methodun
 * olup olmadığı run time'da kontrol edilir ve eğer böyle bir method yok ise run time'da hata alırız.
 */

 /**
 * Method Bağlama vs Listener Bağlama:
 * Bu ikisi arasındaki en büyük fark; bağladığımız listener, olay gerçekleşince(butona tıkladığımızda)
 * değil, listener'ı view'a bağladığımız anda oluşur.
 *
 * Yani biz bir listener'ı dataBinding kullanarak bir view'a bind ettiğimizde, android bu view'a
 * tıkladığımızda gerçekleşecek olan işlemleri bu view ile ilişkilendiriyor ve bu view'a
 * tıklanıldığında bu işlemleri gerçekleştiriyor.

  * Android Dökümanı:
  The major difference between method references and listener bindings is that the actual listener
  implementation is created when the data is bound, not when the event is triggered.
  If you prefer to evaluate the expression when the event happens, use listener bindings.


 */

/**
 * XML'de Expression Kullanımı:
 * android:background="@{isSuccess ? @color/red : @color/green}"
 * Örneğin yukarıdaki kod ile isSuccess değişkeninin değeri true ise, background red olacak,
 * isSuccess değişkenenin değeri false ise background green olacaktır.
 */

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // ViewBinding gibi kullanımı:
        binding.titleTextView.text = "DataBinding in Activity"

        // XML'de tanımlanan variable'a değer atanması:
        binding.person = Person("Kursat", 24)

        // View'a method bağlama:
        binding.toastMessageDisplayer = ToastMessageDisplayer()

        // View'a listener bağlama:
        binding.greeter = Greeter()

        // ArrayList Bağlama:
        /**
         * ArrayList'in tipinin belirlenmesi için xml'de aşağıdaki gibi bir tanımlama yapılır;
         * <variable
         *  name="personList"
         *  type="java.util.ArrayList&lt;Person>"/>
         *  Dikkat edilirse, ArrayList<Person> yerine ArrayList&lt;Person> yazılmıştır.
         *  Yani '<' işareti yerine '&lt;' ifadesinin kullanılması gerekiyor.
         */
        val personList = arrayListOf(Person("ali", 1), Person("mehmet", 2))
        binding.personList = personList

        // Expression Kullanımı:
        binding.isSuccess = true // isSuccess değerine göre background arka planda set edilecek.

        // Obje değeri değiştiğinde view'ında değerinin otomatik değişmesi:
        val kisi = Kisi("Kursat")
        binding.kisi = kisi

        binding.changeNameButton.setOnClickListener {
            kisi.name.set("Yeni Usim")
        }

    }

}

data class Person(val name: String, val age: Int)

class Kisi(name: String) {
    var name = ObservableField(name)
}

class ToastMessageDisplayer {
    fun showToast(view: View) {
        Toast.makeText(view.context, "Toast Message", Toast.LENGTH_SHORT).show()
    }
}

class Greeter {
    fun sayHello(view: View, name: String) {
        Toast.makeText(view.context, "Hello $name", Toast.LENGTH_SHORT).show()
    }
}

