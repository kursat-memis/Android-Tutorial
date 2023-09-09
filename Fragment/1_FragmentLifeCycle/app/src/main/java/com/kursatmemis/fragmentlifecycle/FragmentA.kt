package com.kursatmemis.fragmentlifecycle

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

// Fragment'ı tamamen yıkmadığımız durumlarda: onPause - onStop
// Tekrar açarsak : onStart - onResume
// Detaylı: https://medium.com/@betul.sandikci/android-aktivite-ya%C5%9Fam-d%C3%B6ng%C3%BCs%C3%BC-activity-life-cycle-fragmentl%C4%B1-bir-aktivite-olmak-3a7b39fb32a9

class FragmentA : Fragment() {

    /*
        İlk çağrılan method.
        Bu method ile fragment activity'e eklenerek activity'nin bir parçası haline gelir.
        Parametre olarak eklendiği context'i alır.
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.w("mKm - fragment", "onAttach")
    }

    /*
        Fragment yaratıldığında çağrılan method.
        Fragment'ın başlangıç ayarlarını yapmak ve bu ayarları fragment'ın yıkılıp yeniden
        oluşturma sürecinde parametre olarak aldığı bundle objesiyle tekrar geri yüklemek
        için kullanılabilir.
        Activity içerisinde onCreate methodunda nasıl ki ui bileşenlerine ilk değerlerini atıyorsak
        burada da atayabiliriz. Ancak bunu onCreateView methodunda yapmak daha sağlıklı.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.w("mKm - fragment", "onCreate")

        if (savedInstanceState != null) {
            val name = savedInstanceState.getString("name")
            Log.w("mKm - name", name.toString())
        }
    }

    /*
        Ekranda gözükecek olan fragment tasarımının belirlendiği method'dur.
        Bu method'un return ettiği view, ekranda gösterilir.
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
        Activity'nin yaratılması tamamlandığında çalışacak method'dur.
        Bu method'dan önce calisan methodlar'da, activity üzerindeki yapılara erişmek
        çok sağlıklı değildir. Çünkü activity henüz oluşmamış olabilir.
     */
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.w("mKm - fragment", "onActivityCreated")
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
     */
    override fun onPause() {
        super.onPause()
        Log.w("mKm - fragment", "onPause")
    }

    // Fragment artık kullanıcıya görünür olmadığı durumda çalışan method'dur.
    override fun onStop() {
        super.onStop()
        Log.w("mKm - fragment", "onStop")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.w("mKm - fragment", "onSaveInstanceState")
        outState.putString("name", "kursat")
    }

    // Bu method ile birlikte fragment sonlanma evresine girer.
    override fun onDestroyView() {
        super.onDestroyView()
        Log.w("mKm - fragment", "onDestroyView")
    }

    // Fragment için ayrılan son kaynakların temizlenmesi için çağrılır.
    override fun onDestroy() {
        super.onDestroy()
        Log.w("mKm - fragment", "onDestroy")
    }

    // Fragment bulunduğu activity'den ayrıldığında çağrılır.
    override fun onDetach() {
        super.onDetach()
        Log.w("mKm - fragment", "onDetach")
    }

}