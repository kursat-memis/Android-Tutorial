package com.kursatmemis.navigationcomponent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

/**
 * Not: Konuyu iyi anlatan bir döküman:
 * https://egemen-mede.gitbook.io/jetpack-navigation/
 */

/**
 * Önemli LifeCycle Notu:
 * Diyelim ki A ve B fragment'larımız var. A fragment'ından B fragment'ına bir action'ınımız var.
 * Ve B fragment'ından da A fragment'ına bir actionımız var. A ve B fragment'larının üzerinde
 * bulunan butonlara bastığımızda bu actionlar gerçekleşiyor. Eğer biz A'dan B'ye geçiş yaparsak;
 * A'nın sadece view'ı yıkılır ve kendisi bellekte tutulmaya devam eder. (Detach işlemi.) Haliyle de
 * sadece view'ın yıkıldığı lifecycle methodları A için çalışır. [onPause, onStop, onDestroyView]
 * B ise sıfırdan oluşurulur ve tüm oluşturucu lifecycle methodları çalışır. B fragment'ı üzerindeyken
 * 'back' tuşuna basar isek, B tamamen destroy edilir ve A'nın sadece view'ı yeniden oluşur. (Kendisi
 * hala bellekte tutuluyordu. Sadece view'ı yıkılmıştı.)[onCreateView, onViewCreated, onStart, onResume]
 *
 * Eğer ki A'dan B'ye geçerken, A'yı backstack'den silseydik, A'nın sadece view'ı değil komple
 * kendisi destoroy edilirdi. [Bellekte artık tutulmazdı.]
 *
 * Eğer B fragment'ındayken butona basarak A fragment'ına geçiş yapan action'ı kullanırsak, bu durumda
 * bellekte tutulan eski A fragment'ına geçmeyiz. Yeni bir A fragment'ı bellekte sıfırdan oluşturulur
 * ve biz o fragment'a geçiş yaparız. Yani backstack'de şu durum vardır: [A(eski), B, A(yeni)]
 * Eğer ki biz B'den A'ya geçiş yaparken, yeni bir A fragment'ı oluşturup ona geçmek değil de
 * eski olan A fragment'ına geri dönmek istiyorsak, bu durum da action kullanmayıp tek yapmamız gereken
 * B'yi backstack'den kaldırmak olur. Bunu da B fragment'ı içinde popBackStack() methoduyla yapabiliriz.
 */

/**
 * Nasıl Kullanılır?
 * 1. Aşağıdaki kodlar dependencies'e eklenir. (Güncellemeleri kontrol ederek.)
   (Berlirli sürümlerde hata çıkıyor. 2.5.0 sürümünü kullanmayı öneririm.)

    implementation("androidx.navigation:navigation-fragment-ktx:2.5.0")
    implementation("androidx.navigation:navigation-ui-ktx:2.5.0")

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
      Not: Sürüm numarasının navigation kütüphanesinin sürüm numarasıyla aynı olması gerkiyor.

    buildscript {
        dependencies {
            classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.5.0")
        }
    }

 * 2. Aşağıdaki kod ise build.gradle(module) dosyasında plugins blogunun içine eklenir.
    id("androidx.navigation.safeargs.kotlin")
 * 3. Navigation dosyasında (main_activity_nav) veriyi hangi fragment'a göndermek istiyorsak
 * o fragment seçilir ve sağ taraftaki arguments kısmından gönderilmek istenilen veriler ayarlanır.
 * 4. Veriyi gönderen Fragment'ın kotlin dosyasında, [FragmentName+Directions] adında kotlin
 * tarafından oluşturulmuş class üzerinden navigation dosyasında geçiş işlemine verdiğimiz id'e göre
 * method çağrımı yapılır ve bu methoda parametre olarak göndermek istediğimiz veriler girilir.
 * Arından bu methodun return ettiği NavDirections objesi,
 * Navigation.findNavController(it).navigate(x) methodundaki x parametresine argüman olarak gönderilir.
 * 5. Veriyi alacak olan Fragment kotlin dosyasında, [FragmentName+Args] by navArgs tipinde
 * bir bundle tanımlanır ve bu bundle kullanılarak gönderilen verilere erişim sağlanır.
 */

/**
 * Nested Navigation Graph:
 * Nested navigation (iç içe geçmiş navigasyon), Android Navigation Component ile karmaşık
 * uygulama gezinme işlemlerini kolaylaştırmak ve düzenlemek için kullanılan bir navigasyon
 * yöntemidir.
 * Navigation Graph Panelinde daha modüler çalışabilmek için nested navigation kullanarak belirli
 * bir navigasyon rotasına sahip olan fragment'ları ortak bir nested navigation'da toplayarak
 * daha modüler halde yönetebiliriz. Bu da kodun bakımını, okunurluğunu ve modülerliğini arttırır.
 *
 * Örnek olarak, bir uygulamanızda ana menü, kullanıcı profili, ayarlar gibi farklı bölümler
 * bulunabilir. Her bir bölüm için ayrı bir navigation graph kullanarak, bu bölümleri bağımsız
 * olarak geliştirebilir ve yönetebilirsiniz. Ayrıca, iç içe geçmiş navigasyon grafiği, gezinme
 * işlemlerini daha iyi organize eder ve daha modüler şekilde programlama yapmamıza olanak sağlar.
 *
 * Detaylı Bilgi İçin:
 * https://egemen-mede.gitbook.io/jetpack-navigation/09.-nested-navigation-graphs
 */

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}