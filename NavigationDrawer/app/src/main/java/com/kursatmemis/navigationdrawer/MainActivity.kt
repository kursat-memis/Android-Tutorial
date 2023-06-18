package com.kursatmemis.navigationdrawer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.navigation.NavigationView

/**
 * Nasıl Kullanılır?
 * 1. Dependencies kısmına aşağıdaki satırlar eklenir ve üst sürümlere update edilir.
implementation "androidx.navigation:navigation-fragment-ktx:2.3.3"
implementation "androidx.navigation:navigation-ui-ktx:2.3.3"
 * 2. 'res' klasöründe 'Android Resource Directory' diyerek 'navigation' klasörü oluşturulur.
 * 3. Bu oluşturan directory içerisinde 'Android Resource File' oluşturulur ve buna bir
 * isim verilir. (Örn: main_activity_nav)
 * 4. Oluşturulan navigation file'ı içinde gösterilecek olan fragment'lar oluşturulur.
 * 5. Navigation file'ı içinde sol üst taraftaki '+' iconuna (New Destination) tıklayarak
 * gösterilecek olan fragment'lar navigation file'a eklenir.
 * 6. 'res' klasöründe 'Android Resource Directory' diyerek 'menu' klasörü oluşturulur.
 * 7. 'menu' klasörü içine file oluşturulur (nav_menu) item'lar eklenir.
 * (Bu item'lar nav. drawer üzerinde gösterilecek.)
 * 8. Bu menu item'larının id'si, 5. etapta oluşturulan dosyadaki fragment'ların id'si ile aynı
 * olmalıdır.
 * 9. activity_main.xml dosyasına gidilir. Burada root element Const. Layout yerine Drawer Layout
 * olarak set edilir. (Bir id vermen gerekiyor çünkü daha sonra kodlamada kullanılacak.)
 * 10. Root element olan Drawer Layout'un içine Constraint Layout atılır.
 * 9. Eğer default olarak gelen bir toolbar varsa kaldırılır.
 * (Flamingo sürümünde default toobar yok. O yüzden bu aşamayı geçebilirsin.)
 * 10. Aynı buton eklermiş gibi yeni bir toolbar eklenir. (id ver sonra kullanacaksın.)
 * 11. Constraint Layout içine NavHostFragment eklenir. (Fragment'lar bunun üzerinde gözükecek.)
 * 12. Drawer Layout'un içine NavigationView eklenir. !!! Constraint Layout'un içine değil.
 *     (id ver sonra kullanılacak.) (NavigationView'un genişliği wrap_content olarak ayarla.)
 * 13. NavigationView'ın 'menu' attribute'unu kullanarak daha önce oluşturulmuş menu seçilir.
 * 14. NavigationView'ın layout_gravity attribute'u 'start' olarak set edilir.
 * 15. DrawerLayout'un 'openDrawer' attribute'u 'start olarak set edilir.'
 *
 * -----------------------------------------------------------------------------------------------
 *
 * Navigation Drawer'a Başlık Ekleme:
 * 1. Başlığın görünümünü belirlediğim layout file'ı oluşturulur. (nav_title)
 * 2. onCreate methodundaki setNavTitle() methodundaki gibi kodlamalar yazılır.
 */

class MainActivity : AppCompatActivity() {

    private lateinit var navigationView: NavigationView
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var toolbar: Toolbar
    private lateinit var drawer: DrawerLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bindViews()
        setToggle()
        setNavTittle()
    }

    // Toolbar'ı set eder ve sol üstteki 3 noktaya tıklayarak drawer'in açılmasını sağlar.
    private fun setToggle() {
        NavigationUI.setupWithNavController(navigationView, navHostFragment.navController)
        toolbar.title = "My -Toolbar - Title"
        val toggle =
            ActionBarDrawerToggle(this@MainActivity, drawer, toolbar, 0, 0)
        drawer.addDrawerListener(toggle)
        toggle.syncState()
    }

    // NavigationDrawer'ın başlığı oluşturulur.
    private fun setNavTittle() {
        navigationView.inflateHeaderView(R.layout.nav_title)

        // Eğer başlık üzerindeki component'lere erişilip manipüle edilmek isteniyorsa;
        /*val navTitleView = navigationView.inflateHeaderView(R.layout.nav_title)
        val titleTextView = navTitleView.findViewById<TextView>(R.id.navTitleTextView)
        titleTextView.text = "Changed title."*/

    }

    // Drawer açıkken geri tuşuna basıldığında uygulamayı degilde drawer'i kapatmayı saglar.
    override fun onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun bindViews() {
        navigationView = findViewById(R.id.navigationView)
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        toolbar = findViewById(R.id.toolbar)
        drawer = findViewById(R.id.drawer)
    }

}