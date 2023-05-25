package com.kursatmemis.webview

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.webkit.*
import androidx.appcompat.app.AppCompatActivity


/**
 * Not: Gerçekten ihtiyacımız olmadığı durumlarda kullanmamalıyız. Çünkü performans olarak pahalı
 * bir işlemdir.

 * Webkit: WebKit, açık kaynaklı bir web tarayıcısı motorudur ve birçok tarayıcı ve
 * uygulama tarafından kullanılmaktadır.
 * Webview, web içeriklerini render'lamak (web sayfasının görüntüsünü oluşturmak) için webkit
 * motorunu kullanır ve bu nedenle web sayfasının karmaşık olduğu durumlarda webview, diğer
 * UI componentlerine göre daha fazla bellek tüketebilir.

 * Eğer uygulamalarımızda belirli bir sayfada mobil ile uyumlu web sitesi göstermek istiyorsak
 * WEB VIEW kullanırız.

 * Uzak bir sunucuda yer alan web sitesini gösterebileceğimiz gibi kendi uygulamamız içinde
 * yer alan html sayfalarınıda gösterebiliriz.
 *
 * Nasıl Kullanır?
   1. AndroidManifest.xml dosyasında, uygulamamız için internet bağlantısı izni veririz.
      Aksi halde web sayfaları yüklenmez.
            <uses-permission android:name="android.permission.INTERNET" />
   2. activity_main.xml dosyasında bir WebView tanımlarız.
   3. WebView'ı findViewById() methodu ile buluruz ve bir objesini oluştururuz.
   4. Oluşturulan obje üzerinden loadUrl() methodunu çağırırız ve bu methoda parametre olarak
      görüntülemek istediğimiz sitenin linkini geçeriz. (En başında https:// olmalı.)

 * Webview Class'ı İçinde Tanımlanan Bazı Methodlar:
   1. canGoBack: Bir öncesinde ziyaret ettiğimiz sayfanın olup olmadığı bilgisini tutar.
   2. canGoForward: Gezinme geçmişinde ileriye doğru gidilip gidilemeyeceğinin bilgisini tutar.
   3. clearHistory: Bu method, ileri ve geri gezinme geçmişini temizler.
   4. goBack: Sayfalar arasında gezinirken geri tuşuna bastığımızda bir önceki gezindiğimiz
      sayfaya dönmek istediğimiz zaman kullanacağımız metot.
   4. getTitle: Mevcut sayfanın title'ını return eder.
   5. getUrl: Mevcut sayfanın url'ini return eder.

 * WebViewClient class'ı, webview sayfalarının handle edildiği (gerçekleşen eylemlerin ele
   alındığı) bir class'tır. Bu class sayesinde sayfa yüklenmesi tamamlanana kadar olan süreci
   yönetebilir ve gerekli müdahaleleri yapabiliriz.

 * WebViewClient class'ında override edebileceğimiz methodlar:
   1. onPageStarted: Sayfamız yüklenmeye başladığında yapmak istediğimiz işlemleri
      yazmamız gereken kısım.
   2. onPageFinished: Sayfamızın yüklenme işlemi tamamlandığında yapmak istediğimiz işlemleri
      yazacağımız kısım
   3. onReceivedError: Yükleme işlemi esnasında alınan hata durumlarını belirttiğimiz kısım.
      Hata sonucu dönen hata koduna göre kullanıcıya gerekli bilgilendirmeyi yapabiliriz.
      Bu tür bir durumda hata mesajları yerine uygulama içerisine eklediğimiz html sayfalarını da
      gösterebiliriz.
   4. shouldOverrideUrlLoading: Normalde webview'da gösterilen sitede herhangi bir linke
      tıkladığımızda, bu linke kendi uygulamamız üzerinden değil, sistemin varsayılan olarak
      kullandığı tarayıcıyla gideriz. (Yani sen webview'da bir linke tıkladığında, o link uygu
      laman içinde değil chrome tarayıcısında açılıyor.) Bu durumda kullanıcının deneyimini
      kötü etkileyebilir. Bunun önüne geçmek için bu methodu override ederiz. Bunun override
      edilmesi sayesinde, webview'da gösterilen site üzerindeki linklere tıkladığımızda, bu linkler
      bizim uygulamamız içinde açılacaktır.



 */

class MainActivity : AppCompatActivity() {
    private lateinit var webview: WebView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        webview = findViewById(R.id.webview)


        /*
            Sayfamızda javascript kodları varsa webview objesi üzerinden
            settings.javaScriptEnabled = true atamasını gerçekleştirmeliyiz. Aksi halde
            sayfadaki javascript kodları çalışmayacaktır.

            Not: Eğer WebView üzerinde görüntülenecek olan sayfa güvenilir bir sayfa değilse
            uygulama ve kullanıcının güvenirliği açısından buna false diyerek javascript kodlarının
            çalıştırılmamasını seçebiliriz.
         */
        webview.settings.javaScriptEnabled = true

        /*val baseUrl = "https://www.journaldev.com"
        val data = "Relative Link"
        val mimeType = "text/html"
        val encoding = "UTF-8"
        val historyUrl = "https://www.journaldev.com"

        webview.loadDataWithBaseURL(baseUrl, data, mimeType, encoding, historyUrl)
        webview.loadData("<html><body>Hello, world!</body></html>", "text/html", "UTF-8")*/
        webview.loadUrl("https://www.youtube.com/watch?v=zOqP4adLhto&list=PLSyNCBfGZnH65eKMLKBqHnfcY8A79VL8D")
        webview.getSettings().setAllowContentAccess(true)
        val webSettings: WebSettings = webview.getSettings()
        webSettings.pluginState = WebSettings.PluginState.ON
        webSettings.useWideViewPort = true
        webSettings.loadWithOverviewMode = true
        webview.canGoBack()
        webview.setWebChromeClient(object : WebChromeClient() {})

    }

    class customWebViewClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            view?.loadUrl(url!!)
            return true
        }

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            Log.e("mkmmkm", "onPageStarted")
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            Log.e("mkmmkm", "onPageFinished")
        }

        override fun onReceivedError(
            view: WebView?,
            request: WebResourceRequest?,
            error: WebResourceError?
        ) {
            super.onReceivedError(view, request, error)
            Log.e("mkmmkm", "onPageStarted")
        }
    }

    override fun onBackPressed() {
        if (webview.canGoBack()) {
            webview.goBack()
        } else {
            super.onBackPressed()
        }
    }
}