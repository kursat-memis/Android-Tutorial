package com.kursatmemis.glide

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target


/**
 * Nasıl Kullanılır?
 * 1. Aşağıdaki adreste belirtilen dependency'ler projeye eklenir.

 * https://github-com.translate.goog/bumptech/glide?_x_tr_sl=en&_x_tr_tl=tr&_x_tr_hl=tr&_x_tr_pto=sc

 * 2. AndroidManifest.xml dosyasında internet izni verilir.
 *    <uses-permission android:name="android.permission.INTERNET"/>
 *
 * 3. Glide, istenilen resmi ekranda göstermek için en az 3 parametreye ihtiyaç duyar:
 *    1- with(context): Bir activity veya Fragment nesnesi bekler.
 *    2- load(resource): Ekranda göstermek istediğimiz resmin kaynağını bekler. Bu kaynak,
 *       bir internetteki bir resmin URL'i, cihazdaki bir resmin URI'ı veya proje içinde bulunan
 *       bir kaynak (örn: R.drawable.icon) olabilir.
 *    3- into(target): Ekranda gösterilmek istenen resmin içinde gözükeceği view objesini bekler.
 *
 *
 * Not: 'placeholder' metodunu kullanarak istenilen resim yüklenene kadar başka bir resim göstebiliyoruz
 *       yada resmimiz bulunamadığında, bir hata ile karşılaşıldığında 'error' metodunu kullanarak
 *       ImageView’de bir hata resmi gösterebiliyoruz.
 *
 * Not: Glide'ın, resmi yeniden boyutlama, kırpma, ve dönüştürme gibi birçok özelliği daha bulunmakta.
 *      İhtiyaç olunduğu taktirde internetten nasıl yapıldığı araştırılıp kodumuza entegre edilebilir.
 */

class MainActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        imageView = findViewById(R.id.imageView)

        val imageURL = "https://gifdb.com/images/high/hard-work-hardworking-cute-sticker-typing-2g9uumun7gfuzt3f.gif"

        val progressDrawable = buildProgressDrawable()
        progressDrawable.start()

        Glide.with(this).load(imageURL).placeholder(progressDrawable).error(R.drawable.ic_launcher_background)
            .listener(object : RequestListener<Drawable> {

                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>,
                    isFirstResource: Boolean
                ): Boolean {
                    // Görüntünün yüklenmesi başarısız olduğunda gerçekleştirilecek işlemler
                    progressDrawable.stop()
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable,
                    model: Any,
                    target: Target<Drawable>?,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    // Görüntü başarıyla yüklendiğinde gerçekleştirilecek işlemler
                    // Bu metot, görüntü başarıyla yüklendiğinde çağrılacaktır
                    progressDrawable.stop()
                    return false
                }

            }).into(imageView)

    }

    private fun buildProgressDrawable(): CircularProgressDrawable {
        val circularProgressDrawable = CircularProgressDrawable(this).apply {
            strokeWidth = 10f
            centerRadius = 40f
        }
        return circularProgressDrawable
    }

}