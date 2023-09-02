package com.kursatmemis.dagger.dependency_injection.consumers

import android.util.Log
import com.kursatmemis.dagger.Adres
import javax.inject.Inject

/**
 * @Inject: Dagger tarafından otomatik olarak başlatılmasını istediğimiz objelerin başına yazarız.
 *          Dagger @inject annotasyonu ile belirlenen objelere, modül'de tanımlanan objeyi enjekte
 *          edecektir.
 *
 * Not: Kotlinde eğer constructor'dan önce bir annotation kullanırsak constructor keywordunu yazma-
 * lıyız.
 */

class Kargo @Inject constructor(private val adres: Adres) {

    fun kargoyuGonder() {
        Log.w("mKm - dagger", "Kargo, ${adres.adres} adresine gönderildi.")
    }

}