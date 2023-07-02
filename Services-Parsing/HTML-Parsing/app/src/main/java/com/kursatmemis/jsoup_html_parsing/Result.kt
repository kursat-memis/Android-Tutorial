package com.kursatmemis.jsoup_html_parsing

import android.util.Log
import org.jsoup.Jsoup
import org.jsoup.select.Elements

/**
 * Nasıl Kullanılır?
 *
 * Temel olarak verileri id'e ve class'a bağlı olarak alırız:
 * html <div id=”yourDivName”> elementlerine ulaşabilmek için “#” kullanarak aşağıdaki gibi yazıyoruz.
 * Elements info = doc.select("div#yourDivName");
 * Elements info = doc.select("div[id=yourDivName"); // alternatif olarak bunuda kullanabilirsiniz.
 *
 * html <div class=”yourClassName”> elementine ulaşabilmek için “.” kullanarak aşağıdaki gibi yazıyoruz.
 * Elements info = doc.select("div.yourClassName");
 * Elements info = doc.select("div[class=yourClassName]"); // alternatif olarak bunuda kullanabilirsiniz.
 *
 * Bunlara ek olarak belirli tag'leride alabilir.
 * Örneğin 'meta' adındaki tüm tagleri alan kod:
 * Elements metaElems = doc.select("meta");
 *
 * Bir başka örnek olarak html'deki tüm h2 tag'lerini değilde sadece içinde topic attribute'ü
 * bulunan h2 tag'lerini almak isteyelim. Bunun için kullanmamız gereken kod:
 * Elements topicList = doc.select("h2.topic");
 */

class Result {

    fun news() {
        val url = "https://www.haberler.com/" // Verilerin alınmak istenildiği site linki.

        // Jsoup.connect(url): Belirtilen url'e bağlanılır.
        // timeout(15000): Verilerin alınması için 15 saniye boyunca deneme yapar.
        // Not: Bazı durumlarda veriler hemen alınamayabiliyor ve bunun neticesinde uygulama crash
        // oluyor. Bundan dolayı timeout methodunu kullanmakta fayda vardır.
        // get(): Web sitesinin html içeriği 'Document' objesi olarak programa dahil ediliyor.
        val doc = Jsoup.connect(url).timeout(15000).get()

        // doc.select("meta[name=description]") kodu ile name attribute'ü 'description' olan tag'e
        // gidildi ve attr("content") kodu ile bu tag'in 'content' attribute'ünün içeriği alındı.
        val description = doc.select("meta[name=description]").attr("content")
        Log.w("mkm-des", description.toString())

        // Aşağıda doc.select("div#slides") kodu ile id değeri 'slides' olan elementler alınmış
        // ve ardından select("a") kodu ile bu elementler içinde bulunan 'a' tagleri alınmıştır.
        val elements: Elements = doc.select("div#slides").select("a")

        // Alınan a tagleri üzerinde gezmek için for döngüsü.
        for (element in elements) {
            //  a tag'indeki 'title' attribute'u alınır.
            val title = element.attr("title")
            //  a tag'indeki 'href' attribute'u alınır.
            val href = url + element.attr("href")
            //  a tag'indeki 'img' elementi alınır ve bu elementteki data-src attribute'u alınır.
            val imgSrc = element.select("img").attr("data-src")

            Log.w("mKm-title", title.toString())
            Log.w("mKm-href", href.toString())
            Log.w("mKm-imgSrc", imgSrc.toString())
        }

    }

}


