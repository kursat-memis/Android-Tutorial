package com.kursatmemis.hilt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.kursatmemis.hilt.classes.Musician
import com.kursatmemis.hilt.classes.Student
import com.kursatmemis.hilt.classes.Test1
import com.kursatmemis.hilt.classes.Test2
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Gradle Dependencies:
 * Aşağıdaki resmi dökümana göre gradle dependency'leri eklenir.
 * https://developer.android.com/training/dependency-injection/hilt-android?hl=tr#inject-interfaces
 */

/**
 * Hilt Nasıl Kullanılır?
 * 1. 'Application' class'ından türeyen bir class oluşturulur ve bu class'ın başına @HiltAndroidApp
 *     annotation'ı eklenir. Ardından manifest dosyasında <application> tag'i içinde 'android:name'
 *     attribute'une değer olarak bu class'ın adı verilir. (HiltApplication)
 *     @HiltAndroidApp annotation'ına sahip bir Application class'ı oluşturmak, Hilt'in
 *     dependency'leri yönetmesi için oluşması gereken kodların otomatik olarak generate edilmesini
 *     tetikler.
 *
 * 2. Dependency'lerinin hilt tarafından sağlanarak oluşturulmasını istediğimiz objenin(musician)
 *    tanımlandığı android sınıfının(MainActivity) üstüne @AndroidEntryPoint annotation'ınını
 *    ekleriz.
 *    Bu objenin hilt tarafından otomatik olarak oluşturulmasını android'e belirtmek için bu objenin
 *    başına @Inject annotation'ı koyarız. Ayrıca bu objenin class'ının ihtiyaç duyduğu
 *    dependency'lerinde hilt tarafından oluşması için bu objenin constructor'ına ve dependency'lerin
 *    constructor'larının başına @Inject annotation'ı ekleriz. Bu @Inject annotation'ı sayesinde
 *    hilt, hangi objeleri otomatik olarak başlatması gerektiğini bilir.
 *    Not: Hilt tarafından inject edilecek objeler private olamaz. (musician private olamaz.)
 *         Ancak class constructor'ındaki parametreler private olabilir.
 *
 * Not: 3 çeşit injection yöntemi vardır:
 * 1. Field Injection: Dependency'nin, class field'ında sağlandığı injection. (musician)
 * 2. Constructor Injection: Dependency'nin, class constructor'ında sağlandığı injection.
 *    (Musician class'ının constructor'ındaki dependency'lerin sağlanması)
 * 3. Method Injection: Dependency'lerin bir method aracılığıyla sağlandığı injection.
 *    (Musician class'ındaki setDependency() methodunda değendency'lerin sağlanması.)
 *
 * Not:
 * Bir Android sınıfına @AndroidEntryPoint ile ek açıklama eklerseniz bu sınıfı kullanan
 * Android sınıflarına da ek açıklama eklemeniz gerekir.
 * Örneğin, bir fragment'a ek açıklama eklerseniz bu fragment'ı kullandığınız tüm activitiy'lere de
 * ek açıklama eklemeniz gerekir.
 *
 * Not:
 * ViewModel'da, Activity-Fragment gibi yapılardada kullandığımız @AndroidEntryPoint annotation'ının
 * yerine @HiltViewModel annotation'ınını kullanırız.
 */

/**
 * Hilt Modulü:
 * Hilt bazı durumlarda dependency'leri nasıl sağlayacağını bilmez. Mesela Student class'ından
 * bir obje oluşturmak istediğimizde bu class'ın constructor'ına bir String değer girmemiz gerekir.
 * Hilt Student objesi oluştururken bu girilmesi gereken String değerin ne olduğuna karar
 * veremeyeceğinden dolayı, student objesini oluşturamaz. İşte tam burada hilt'e Student objesini
 * oluştururken, constructor'daki String parametresini nasıl başlatacağını belirtmemiz için modül
 * kullanırız.
 *
 * Not: Modülün nasıl oluşturulacağı ve kullanılacağı Student class'ında belirtildi.
 */

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    // Field injection
    @Inject
    lateinit var musician: Musician

    @Inject
    lateinit var student: Student

    @Inject
    lateinit var test1: Test1
    @Inject
    lateinit var test2: Test2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        musician.sing()
        student.study()

        Log.w("mKm - hilt", "test1.value: ${test1.value}")
        Log.w("mKm - hilt", "test2.value: ${test2.value}")
    }
}




