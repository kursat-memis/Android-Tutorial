package com.kursatmemis.layoutinflater

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.LinearLayout

/**
 * Layout Inflation Nedir?
 * XML dosyasını, Kotlin'deki View objelerine dönüştürme işlemidir.
 * Bu işlem, zaman alan ve kaynak tüketen, yani maliyetli bir işlemdir.
 * Çok maliyetli olmasından dolayı gerçekten ihtiyaç olunmadığı durumlarda kullanılması tavsiye
 * edilmez.
 * Bu yapı sadece precompied XML'lerde çalışır.
 * (Yani inflate edilecek layout dosyası, layout klasörü altında oluşturulmuş olmalıdır.)
 */

/**
 * Layout Params Nedir?
 * Bir view'ın ebeveyni olan ViewGroup içinde nasıl yerleşeceğini belirtmek için kullanılan
 * bir sınıftır.
 *
 * Bir View'in layout parametreleri, view'ın genişliği, yüksekliği, ağırlığı, yerleşimi gibi
 * özelliklerini kontrol etmeye yardımcı olur
 */

/**
 * addView():
 * Bu method kullanılarak kotlin objesine dönüştürülen view, bir ViewGroup'a eklenebilir.
 * Not: Eklenen view'ın layoutParams özellikleri (weight, height vb.), eklendiği view'ın
 * özellikleri olarak gelecektir.
 *
 * Örnek:
 * rootLayout.addView(my_text_view): Bu kod ile, rootLayout'a, my_text_view eklenir ve textview'ın
 * layout params özellikleri, rootLayout ile aynı olur.
 */

/**
 * inflate() Methodu:
 *
 * Senaryo - 1: (Bu yöntemin kullanılması tavsiye edilmez.)
 * val v = layoutInflater.inflate(R.layout.my_text_view, null)
 * What 'v' contains? --> my_text_view
 * Parent of 'v' --> null
 * LayoutParams --> null
 *
 * Senaryo - 2:
 * val v = layoutInflater.inflate(R.layout.my_text_view, childLayoutLinearLayout)
 * What 'v' contains? --> childLayoutLinearLayout
 * Parent of 'v' --> rootElementFrameLayout
 * LayoutParams --> rootElementFrameLayout
 *
 * Senaryo - 3: (3. parametre olarak true geçmek herhangi bir değişikliğe sebep olmaz.)
 * val v = layoutInflater.inflate(R.layout.my_text_view, icerdekiLayout, true)
 * What 'v' contains? --> childLayoutLinearLayout
 * Parent of 'v' --> rootElementFrameLayout
 * LayoutParams --> rootElementFrameLayout
 *
 * Senaryo - 4: Not: Bu yöntemde sistem textview'ı otomatik olarak icerdekiLayout'a eklemez.
 *                   Bunu biz addView methodu kullanarka yapmalıyız.
 * val v = layoutInflater.inflate(R.layout.my_text_view, childLayoutLinearLayout, false)
 * What 'v' contains? --> my_text_view
 * Parent of 'v' --> null
 * LayoutParams --> childLayoutLinearLayout
 */

class MainActivity : AppCompatActivity() {

    private lateinit var rootElementFrameLayout: FrameLayout
    private lateinit var childElementLinearLayout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rootElementFrameLayout = findViewById(R.id.rootElementFrameLayout)
        childElementLinearLayout = findViewById(R.id.childElementLinearLayout)

        // Layout Inflater Edinme Yöntemleri
        val layoutInflater1 = layoutInflater // Yöntem - 1
        val layoutInflater2 = LayoutInflater.from(this) // Yöntem - 2
        val layoutInflater3 =
            getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater // Yöntem -3
        // Not: 3. yöntemde kullanılan  getSystemService methodu bize bir Object-Any objesi
        // return eder. Bunu manuel olarak LayoutInflater objesine cast etmemiz gerekiyor.

        // inflateMethod1(layoutInflater1)
        // inflateMethod2(layoutInflater1)
        // inflateMethod3(layoutInflater1)
        inflateMethod2(layoutInflater1)


    }

    private fun inflateMethod1(layoutInflater1: LayoutInflater) {
        // Aşağıdaki kod ile beraber, my_text_view layout dosyası kotlin objesine dönüştürülür
        // ve return edilerek view adlı değişkene atanır.
        val view = layoutInflater1.inflate(R.layout.my_text_view, null)
        Log.e("mKm - View", view.toString()) // my_text_view
        Log.e("mKm - Parent", view?.parent.toString()) // null
        Log.e("mKm - LayoutParams", view?.layoutParams.toString()) // null


        rootElementFrameLayout.addView(view)
        Log.e("mKm - View", view.toString()) // my_text_view
        Log.e("mKm - Parent", view.parent.toString()) // rootElementFrameLayout
        Log.e("mKm - LayoutParams", view.layoutParams.toString()) // rootElementFrameLayout

        rootElementFrameLayout.removeView(view)

        childElementLinearLayout.addView(view)
        Log.e("mKm - View", view.toString()) // my_text_view
        Log.e("mKm - Parent", view.parent.toString()) // childElementLinearLayout
        Log.e("mKm - LayoutParams", view.layoutParams.toString()) // childElementLinearLayout
    }

    private fun inflateMethod2(layoutInflater1: LayoutInflater) {
        // Aşağıdaki kod ile beraber, my_text_view layout dosyası kotlin objesine dönüştürülür
        // ve return edilerek view adlı değişkene atanır. Buna ek olarak, childElement'in üzerine
        // eklenir.
        val view = layoutInflater1.inflate(R.layout.my_text_view, childElementLinearLayout)
        Log.e("mKm - View", view.toString()) // childElementLinearLayout
        Log.e("mKm - Parent", view?.parent.toString()) // rootElementFrameLayout
        Log.e("mKm - LayoutParams", view?.layoutParams.toString()) // rootElementFrameLayout

    }

    private fun inflateMethod3(layoutInflater1: LayoutInflater) {
        // Aşağıdaki kod ile beraber, my_text_view layout dosyası kotlin objesine dönüştürülür
        // ve return edilerek view adlı değişkene atanır. Buna ek olarak, childElement'in üzerine
        // eklenir. (3. parametre olarak true geçmek bir şey değiştirmez.)
        val view = layoutInflater1.inflate(R.layout.my_text_view, childElementLinearLayout, true)
        Log.e("mKm - View", view.toString()) // childElementLinearLayout
        Log.e("mKm - Parent", view?.parent.toString()) // rootElementFrameLayout
        Log.e("mKm - LayoutParams", view?.layoutParams.toString()) // rootElementFrameLayout
    }

    private fun inflateMethod4(layoutInflater1: LayoutInflater) {
        // Aşağıdaki kod ile beraber, my_text_view layout dosyası kotlin objesine dönüştürülür
        // ve return edilerek view adlı değişkene atanır. Ancak textview, childElement'e eklenmez..
        // Bunu addView() methodu kullanarak biz yapmalıyız.

        val view = layoutInflater1.inflate(R.layout.my_text_view, childElementLinearLayout, false)
        Log.e("mKm - View", view.toString()) // my_text_view
        Log.e("mKm - Parent", view?.parent.toString()) // null
        Log.e("mKm - LayoutParams", view?.layoutParams.toString()) // childLayoutLinearLayout

        childElementLinearLayout.addView(view)

        Log.e("mKm - View", view.toString()) // my_text_view
        Log.e("mKm - Parent", view?.parent.toString()) // childLayoutLinearLayout
        Log.e("mKm - LayoutParams", view?.layoutParams.toString()) // childLayoutLinearLayout
    }

}