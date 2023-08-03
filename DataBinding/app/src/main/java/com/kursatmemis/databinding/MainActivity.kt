package com.kursatmemis.databinding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.kursatmemis.databinding.R
import com.kursatmemis.databinding.databinding.ActivityMainBinding

/**
 * DataBinding Temel Kullanim
 * 1. build.gradle(Module:App) dosyasına aşağıdaki kod eklenir:

buildFeatures{
dataBinding = true
}

 * 2. Layout dosyası içindeki xml kodları, <layout> tagleri arasına alınır ve namespace'ler,
 * <layout ....> kısmındaki noktalı yerde tanımlanır.
 * 3. Bu işlemlerden sonra android studio tarafından otomatik olarak Layout dosyasının adına göre
 * bir binding class'ı oluşturulur. (activity_main.xml ----> ActivityMainBinding)
 * 4. Otomatik olarak oluşturulmuş olan bu binding class'ından bir obje oluşturulur.
 * 5. setContentView(R.layout.activity_main) satırı,
 * DataBindingUtil.setContentView(this, R.layout.activity_main) satırı ile değiştirilir.
 * 5. Oluşturulan bu binding objesi üzerinden layout dosyasındaki view'lara erişilir.
 */

/**
 * XML'de Değişken Tanımlama:
 * 1. XML dosyasındaki <data> </data> tagleri arasında <variable> </variable> tagleri oluşturulur.
 * 2. Bu tagi oluşturunca xml bizden otomatik olarak 'name' ve 'type' field'larını doldurmamızı
 * ister.
 * 'name' kısmına oluşturmak istediğimiz değişkenin adını yazıyoruz.
 * 'type' kısmına ise oluşturmak istediğimiz değişkenin tipini yazıyoruz. Eğer bu değişken tipi,
 * bizim oluşturduğumuz bir class ise; type kısmına, [packagename.classname] yazıyoruz.
 * packagename: Class'ın bulunduğu package adı.
 * classname: Class adı.
 * 3. Ardından bu değişkeni atamak istediğimiz component'in attribute'une atıyoruz. (@{} formatı ile.)
 * Örneğin değişken adımız 'age' ise ve bunu textView'ın text attribute'une atamak istiyorsak;
 * android:text="@{age}" şeklinde bir atama yaparız.
 */

/**
 * XML'de Expression Kullanımı:
 * android:background="@{isSuccess ? @color/red : @color/green}"
 * Örneğin yukarıdaki kod ile isSuccess değişkeninin değeri true ise, background red olacak,
 * isSuccess değişkenenin değeri false ise background green olacaktır.
 */

/**
 * View'a Method Bağlama:
 * XML içinde nethodun bulunduğu class tipinde bir variable tanımlanır(örn: handler).
 * Ardından kotlin kodu ile bu değişkene değer atanır. (binding.handler = EventHandler())
 * Daha sonrasında butonun onClick attribute'une bu method atanır.
 * (android:onClick="@{handler::buttonOnClick}")
 *
 * Not: Aslında dataBinding kullanmadanda bu işlem yapılabilirdi. Mesela onClick attribute'une
 * direkt methodun adı verilip(onClick = buttonOnClick, butona tıklanıldığında bu methodun
 * çalışması sağlanabilirdi. Ancak burada buttonOnClick methodunun MainActivity class'ı içinde
 * tanımlanmış olması gerekirdi. Eğer ki biz tasarımsal kodlamalarımızı MainActivity içinde
 * tutmak istemiyorsak, burada butona tıknalıldığında çalışacak olan methodu başka bir class'da
 * tanımlar ve dataBinding sayesinde bu başka class'daki methodu butonun onClick'ine bağlayabiliriz.
 */

/**
 * View'a Listener Ekleme:
 * XML içinde nethodun bulunduğu class tipinde bir variable tanımlanır(örn: handler).
 * Ardından kotlin kodu ile bu değişkene değer atanır. (binding.handler = EventHandler())
 * Daha sonrasında butonun onClick attribute'une bu method atanır.
 * android:onClick="@{(view) -> handler.buttonOnClick(view)}"
 * Not: Burada listener eklerken lambla expression kullanılır.
 *
 * Not: View'a buton bağlama ve listener bağlama arasındaki fark 'BAGLANTİNİN NE ZAMAN OLUSTUGUDUR.'
 * Yani view'a buton bağladığımızda, bağlama işlemi, veri ve ui arasında köprü oluşturulduğunda
 * oluşur. Ancak listener bağlamamız ise, olay gerçekleştiğinde olay gerçekleştiğinde olayın
 * dinlenilmesini sağlayan kodu ifade eder. Eğer bir ifade bir olay gerçekleştirildikten sonra
 * değerlendirilecekse o zaman Listener Bağlayıcıları kullanılmalıdır.
 */

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // binding objesi üzerinden view'lara erişme.
        binding.textView1.text = "Data Binding!!!"

        // xml'de primitive bir değişken tanımlayıp onu view'a bağlama.
        binding.myPrimitiveVariable = 10

        // xml'de referans tipli bir değişken tanımlayıp onu view'a bağlama.
        // Not: myObj'de yapılan değişikler dinamik olarak textview'ın text'ine de yansır.
        val myObj = MyClass("First Name")
        binding.myReferanceVariable = myObj
        myObj.name = "Second Name" // Ekranda Second Name görünür.

        // Expression Kullanımı
        binding.isSuccess = true

        // View'a method bağlama - Listener ekleme. (Aralarındaki farkı xml'de görebilirsin.)
        binding.handler = EventHandler()

    }



}

data class MyClass(var name: String)