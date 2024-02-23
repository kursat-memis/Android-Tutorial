package com.kursatmemis.fragmentlifecycle

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

// Fragment'ı tamamen yıkmadığımız durumlarda: onPause - onStop
// Tekrar açarsak : onStart - onResume
// Detaylı: https://medium.com/@betul.sandikci/android-aktivite-ya%C5%9Fam-d%C3%B6ng%C3%BCs%C3%BC-activity-life-cycle-fragmentl%C4%B1-bir-aktivite-olmak-3a7b39fb32a9

class FragmentA : Fragment() {

    /*
        İlk çağrılan method.
        Bu method ile fragment activity'e eklenerek activity'nin bir parçası haline gelir.
        Parametre olarak eklendiği context'i alır.

        Bu method içinde yapılabilecekler;
        Parametre olarak aldığı context üzerinden activity'e erişilebilir.
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)

        val activity = context as MainActivity
        activity.f()

        Log.w("mKm - fragment", "onAttach")
    }

    /*
        Fragment yaratıldığında çağrılan method.
        Fragment'ın başlangıç ayarlarını yapmak ve bu ayarları fragment'ın yıkılıp yeniden
        oluşturma sürecinde parametre olarak aldığı bundle objesiyle tekrar geri yüklemek
        için kullanılabilir.
        Genel olarak bu method'da aşağıdaki işlemleri yapabiliriz:
        - Fragment içinde kullanılacak başlangıç değerlerin (örneğin, fragment'e iletilen argümanlar)
        ayarlanması.
        - Fragment içinde kullanılacak veri yapılarının (örneğin, liste, dizi, vb.) oluşturulması
        ve başlatılması.
        - Eğer fragment veritabanı vb. işlemleri yapacaksa, veritabanı bağlantısının oluşturulması
         veya gerekli ön hazırlıkların yapılması.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.w("mKm - fragment", "onCreate")

        // Başlangıç verilerini ayarla
        val initialText = "Bu bir başlangıç metnidir."
        val args = Bundle()
        args.putString("text", initialText)
        arguments = args

        if (savedInstanceState != null) {
            val name = savedInstanceState.getString("name")
            Log.w("mKm - name", name.toString())
        }

    }

    /*
        Ekranda gözükecek olan fragment tasarımının belirlendiği method'dur.
        Bu method'un return ettiği view, ekranda gösterilir.
        Veritabanı gibi işlemleri bu method'da yapmak yerine onCreate'de yapmak daha sağlıklıdır.
        Çünkü bu methodun kullanım amacı UI ile ilgili işlemler yapmaktır. Ayrıca veritabanı
        gibi geç yapılabilen işlemlerin bu method'da yapılması UI'ın gösterimi sırasında vb.
        problem çıkarabilir.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.w("mKm - fragment", "onCreateView")
        val view = layoutInflater.inflate(R.layout.fragment_a, container, false)
        return view
    }

    /*
        Fragment'daki view'ların yaratılması tamamlandığında çalışacak method'dur.
        Bu method'dan önce calisan methodlar'da, view'lara erişmek
        çok sağlıklı değildir. Çünkü view'lar henüz oluşmamış olabilir.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.w("mKm - fragment", "onViewCreated")
    }

    // Daha önce kaydettiğimiz durumları bu method ile geri alabiliriz.
    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        Log.w("mKm - fragment", "onViewStateRestored")
        Log.w("mKm - name", savedInstanceState?.getString("name").toString())
    }

    // Fragment görünür olduğunda çağrılan method'dur.
    override fun onStart() {
        super.onStart()
        Log.w("mKm - fragment", "onStart")
    }

    // Fragment'ın kullanıcıyla etkileşime geçtği method'dur.
    override fun onResume() {
        super.onResume()
        Log.w("mKm - fragment", "onResume")
    }

    /*
        Fragment'ın kullanıcıyla etkileşimi sonlandığında çağrılan method'dur.
        Bu method'un çalışması için, ya activity duraklatışmış, ya fragment activity'den
        kaldırılmış ya da activity içinde bu fragment yerini başka bir fragment'a bırakmıştır.
        Kullanıcı ile etkileşime bağlı işlemleri durdurmak ve kaynakları serbest bırakmak için kullanılabilir.
        Animasyonları durdurmak için vb. kullanılabilir.
     */
    override fun onPause() {
        super.onPause()
        Log.w("mKm - fragment", "onPause")
    }

    // Fragment artık kullanıcıya görünür olmadığı durumda çalışan method'dur.
    // Kullanıcı ile etkileşime bağlı işlemleri durdurmak ve kaynakları serbest bırakmak için kullanılabilir.
    // Animasyonları durdurmak için vb. kullanılabilir.
    override fun onStop() {
        super.onStop()
        Log.w("mKm - fragment", "onStop")
    }

    // Bu method, fragment yıkıldığında ya da pause edildiğinde verileri kaydetmek için kullanılır.
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.w("mKm - fragment", "onSaveInstanceState")
        outState.putString("name", "kursat")
    }

    // Bu method ile birlikte fragment sonlanma evresine girer.
    // Burada fragment'ın view'ı ile ilgili referanslar serbest bırakılabilir.
    // Örneğin viewBinding objesinin null'a atanması gibi.
    override fun onDestroyView() {
        super.onDestroyView()
        Log.w("mKm - fragment", "onDestroyView")
    }

    // Fragment için ayrılan son kaynakların temizlenmesi için çağrılır.
    // Kullanılmayan kaynakları serbest bırakmak veya diğer temizleme işlemlerini gerçekleştirmek
    // için kullanılabilir.
    override fun onDestroy() {
        super.onDestroy()
        Log.w("mKm - fragment", "onDestroy")
    }

    // Fragment bulunduğu activity'den ayrıldığında çağrılır.
    // Fragment artık aktiviteye bağlı olmadığı için, aktiviteye olan referanslar temizlenir ve
    // fragmentin bellekten kaldırılması için gereken diğer işlemler yapılabilir.
    override fun onDetach() {
        super.onDetach()
        Log.w("mKm - fragment", "onDetach")
    }

}