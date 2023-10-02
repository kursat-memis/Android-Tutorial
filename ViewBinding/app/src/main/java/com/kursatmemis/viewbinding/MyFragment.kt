package com.kursatmemis.viewbinding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kursatmemis.viewbinding.databinding.FragmentMyBinding

/**
 * Fragment'da ViewBinding Kullanımı:
 * 1. Fragment'da, binding objesi tanımlanır.
 * (Layout dosyasının ismine göre: fragment_my.xml — — — — — > FragmentMyBinding)
 * 2. binding objesi onCreateView() methodunda başlatılır.
 * 3. Artık bu binding objesi üzerinden layout dosyasındaki view'lara erişebiliriz.
 * 4. onDestoryView() methodunda binding objesine null değer atanır.
 */

class MyFragment : Fragment() {

    /**
     * Neden _binding ve binding adlı 2 değişken var?
     * Eğer binding adında tek bir obje kullanacak olsaydık bellek sızıntısını engellemek adına
     * onDestroyView() methodunda binding objesine null değer atayacağımız için bu binding objesini
     * nullable olarak tanımlamak zorunda kalırdık. Bundan dolayıda, onCreateView() methodu içinde
     * bu binding objesini kullanarak, layout dosyasındaki view'lara erişirken sürekli '?'
     * ifadesini kullanmak durumunda kalacaktık. (binding?.textView?.text = "My Text")
     * İşte _binding ve binding adlı 2 değişken kullanmak bizi sürekli '?' ifadesini kullanarak
     * kodlama yapmaktan kurtarıyor.
     */
    private var _binding: FragmentMyBinding? = null
    private val binding get() = _binding!! // binding değişkeninin çağrıldığı yere _binding!! ifadesi retur edilir.

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyBinding.inflate(layoutInflater, container, false)

        // _binding?.textView?.text = "ViewBinding in Fragment"
        binding.textView.text = "ViewBinding in Fragment"

        return _binding?.root
    }


    /**
     * Neden Binding Objesine Null atamalıyız?
     * Fragment'lar destroy edilse bile Fragment'ların objeleri bellekte hala varlıklarını
     * sürdürüyorlar. Haliyle bu fragment objelerinin içindeki binding objeleride bellekte yer
     * kaplıyorlar. İşte destory edilen fragment'ların binding objelerinin bellekte gereksiz yer
     * kaplamalarını önlemek için onDestoryView() methodunda bu objelere null değeri atıyoruz.
     *
     * Not: Destory edilen activity'lerin objeleri bellekte kalmaya devam etmediğinden dolayı
     * bu null atama işlemini activity'ler içinde yapmamıza gerek yok. Garbage Collector bu acitivty
     * örneklerini bellekten otomatik olarak temizleyecektir.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}