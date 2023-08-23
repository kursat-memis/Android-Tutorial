package com.kursatmemis.navigationcomponent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

/**
 * Nasıl Kullanılır?
 * 1. Aşağıdaki kodlar dependencies'e eklenir. (Güncellemeleri kontrol ederek.)

implementation("androidx.navigation:navigation-fragment-ktx:2.3.3")
implementation("androidx.navigation:navigation-ui-ktx:2.3.3")

 * 2. res klasorüne sağ tıklayarak 'Android Resource Directory' seçilir. Ardından resource type
 * kısmı navigation olarak ayarlanır ve navigation klasörü oluşturulur.
 * 3. navigation klasöründe 'Navigation Resource File' oluşturulur. (main_acitivity_nav)
 * 4. Navigation Component üzerinde gösterilecek olan fragment'lar oluşturulur.
 * 5. Oluşturulan navigation dosyasına(main_activity_nav) giderek oluşturulan fragment'lar,
 * 'New Destination' ile dosyaya eklenir.
 * 6. Navigation dosyasını main activity'de göstermek için activity_main layout dosyasına gidilir ve
 * NavHostFragment componenti eklenir.
 *
 * 7. Fragment'lar arası geçişi sağlamak için; navigation dosyasında, mevcut fragment'tan hedef
 * fragment'a bir ok çıkarılarak bir geçiş işlemi tanımlanır. Ardından bu geçiş işlemine bir id
 * verilir. Daha sonra mevcut fragment'da hangi view'a tıklanıldığında hedef fragment'a geçmek
 * istiyorsak o view'ın onClickListener methodu içine;
 * Navigation.findNavController(view).navigate(id)
 * kodu yazılır.
 *
 * view: Geçiş işlemini tetikleyecek olan view objesi. (Button vb.)
 * id: Navigation dosyasında tanımladığımız geçiş işleminin id'si.
 *
 * 8. Back Stack yönetimi için, nav dosyasında geçiş işlemini temsil eden oka tıklanır.
 * Bu geçiş işlemi gerçekleşirken back stack'ten silinmesini istediğimiz fragment, sağ taraftaki
 * 'popUpTo' özelliğini kullanarak belirlenir ardından popUpToInclusive özelliği true olarak
 * ayarlanır. Bunun neticesinde ilgili geçiş işlemi gerçekleştirilirken belirtilen fragment
 * back stack'den silinir.
 */

/**
 * Sayfalar Arası Geçişte Veri Transferi:
 * 1. Aşağıdaki kod build.gradle(project) dosyasında en üste eklenir.
    buildscript {
        dependencies {
            classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.7.0")
        }
    }
 * 2. Aşağıdaki kod ise build.gradle(module) dosyasında plugins blogunun içine eklenir.
    id("androidx.navigation.safeargs.kotlin")
 * 3. Navigation dosyasında (main_activity_nav) veriyi hangi fragment'a göndermek istiyorsak
 * o fragment seçilir ve sağ taraftaki arguments kısmından gönderilmek istenilen veriler ayarlanır.
 * 4. Verinin gönderileceği Fragment'ın kotlin dosyasında, [FragmentName+Directions] adında kotlin
 * tarafından oluşturulmuş class üzerinden navigation dosyasında geçiş işlemine verdiğimiz id'e göre
 * method çağrımı yapılır ve bu methoda parametre olarak göndermek istediğimiz veriler girilir.
 * Arından bu methodun return ettiği NavDirections objesi,
 * Navigation.findNavController(it).navigate(x) methodundaki x parametresine argüman olarak gönderilir.
 * 5. Veriyi alacak olan Fragment kotlin dosyasında, [FragmentName+Args] by navArgs tipinde
 * bir bundle tanımlanır ve bu bundle kullanılarak gönderilen verilere erişim sağlanır.
 */

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}