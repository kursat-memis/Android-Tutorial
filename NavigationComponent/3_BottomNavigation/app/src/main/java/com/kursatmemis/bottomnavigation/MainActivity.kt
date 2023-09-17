package com.kursatmemis.bottomnavigation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.kursatmemis.bottomnavigation.databinding.ActivityMainBinding


/**
 * Nasıl Kullanılır?
 * 1. Aşağıdaki kodlar build.gradle(module) dosyasında dependencies'e eklenir.
implementation("androidx.navigation:navigation-fragment-ktx:2.3.3")
implementation("androidx.navigation:navigation-ui-ktx:2.3.3")
 * 2. Bottom Navigation üzerindeki menulere tıkladığımızda gösterilecek olan fragment'lar oluşturulur.
 * 3. res klasörüne sağ tıklayarak 'Android Resource Directory' seçeneği seçilir ve type kısmı
 * 'navigation' olarak ayarlanarak bir navigation klasörü oluşturulur.
 * 4. 'navigation' klasörü içinde navigation dosyası(main_activity_nav) oluşturulur.
 * 5. Navigation dosyasında (main_activity_nav) 'New Destination' iconuna tıklayarak ekranda
 * gösterilmek istenilen fragment'lar eklenir.
 * 6. Bottom Navigation üzerinde gösterilecek menüler oluşturulur. Bunun için once res klasorüne
 * sağ tıklayarak yeni bir 'Android Resource Directory' seçeneği seçilir ve type kısmını 'menu'
 * seçilerek bu klasör oluşturulur.
 * 4. Bu menu klasorü içine menü dosyası oluşturulur. (bottom_bar_menu)
 * 5. Bottom Bar'da gösterilecek olan itemlar, bottom_bar_menu dosyasında oluşturulur.
 *    (Not: Buradaki menu itemları ve navigation dosyasındaki(main_activity_nav) fragment'ların
 *    idleri aynı olmalıdır. Bu sayede ilgili menu item'a tıklanıldığında menu item'ı için hangi
 *    id tanımlanmışsa o id'e ait fragment ekranda gösterilecektir.)
 * 6. İstenilirse menu itemlarına icon verilebilir.
 * 7. main_activity.xml layoutuna BottomNavigationView componenti eklenir(bottomNav) ve bunun menu
 * özelliğine öncesinde oluşturduğumuz menu atanır. Ardından bu component sayfanın en altında
 * görüntülenecek şekilde constraint'lenir.
 * 8. Ardıdan main_activity.xml layoutuna, üzerinde fragment'ları tutacak olan NavHostFragment
 * componenti eklenir.
 * 9. Son olarak bottomNav üzerinde seçilen item'lara göre navHostFragment'da fragment'ların
 * gösterilmesi için gerekli kodlamalar MainActivity class'ında yapılır.
 */


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment

        NavigationUI.setupWithNavController(binding.bottomNav, navHostFragment.navController)
    }
}