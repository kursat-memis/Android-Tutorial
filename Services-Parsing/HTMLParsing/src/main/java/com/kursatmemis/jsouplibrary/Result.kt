package com.kursatmemis.jsouplibrary

import android.util.Log
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements


class Result {
    fun news() {
        val url = "https://www.haberler.com/" // Nereyi ziyaret edeceğim?
        val doc: Document = Jsoup.connect(url).timeout(15000).get()
        // Document: html etiketlerini tutabilen bir tür.
        // Jsoup.connect(url) : url'deki sayfaya git ve onun html elementlerini getir.
        // Timeout: Eğer ilgili html'leri bulamazsan, 15 saniye boyunca bulmayı dene.
        // Not: Yazdıklarımdan tam emin değilim internetten kontrol et.
        Log.d("mkm_msg", doc.html()) // Sayfadaki tüm html'i getirir.
        Log.d("mkm_msg", doc.title()) // Sayfadaki html'den, etiketi title olanı getirir.
        val elements: Elements = doc.getElementsByAttribute("data-headlinenumber") // Attribute'u x olan elementleri bana getir.
        for (item in elements) {
            Log.d("mkm_msg", item.toString())
            val titleAttr = item.getElementsByAttribute("title")
            val title = titleAttr.attr("title")
        }

    }
}