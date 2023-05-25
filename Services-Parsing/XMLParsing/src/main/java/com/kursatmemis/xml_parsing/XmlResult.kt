package com.kursatmemis.xml_parsing

import android.util.Log
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements

/**
     * 0. Manifes.xml dosyasında internet izni alınır ve Jsoup kütüphanesi dependencies'a eklenir.
 * 1. Sitenin xml adresini veren url belirlenir.
 * 2. Document objesi oluşturulur. (Buradaki Document class'ı, jsoup kütüphanesindekidir.)
 *
 *      Jsoup.connect(url).timeout(15000).ignoreContentType(true).get()
 *      -> connect(url): Belirtilen url adresine bağlanarak XML belgesini almak için
 *         bir istek gönderir. Web sayfalarındaki HTML veya XML içeriği JSoup ile kullanılabilir
 *         hale getirilebilir.
 *      -> timeout(): İsteğin cevap vermesi için beklenen maksimum süreyi belirler.
 *      -> ignoreContentType(): Web sayfasından gelen içeriğin türünün (content type)
 *         ne olursa olsun alınmasını sağlar. Bu sayede, JSON, XML, metin vb.
 *         her türlü içeriği alabiliriz.
 *         (Bu parametreye true geçmezsek sadece html içeriklerini return eder.)
 *      -> get(): Yapılan isteği tamamlar ve sonuç olarak bir Document nesnesi döndürür.
 *
 * 3. Document objesi üzerinden getElementsByTag methodunu çağrılır ve istenilen tag'e sahip
 * element alınır. Eğer o tag'e sahip birden fazla element var ise bu elementlerin
 * hepsi alınır. (Bu durumda bir loop kullanarak bu elementler üzerinde gezinebiliriz.)
 *
 *
 *
 * text(): Bu methodu çağıran elementin içeriğindeki text'i return eder.
 *         Örneğin: <name>Ms. Coder</name>
 *         'Ms. Coder' değerini almak için, text() methodunu kullanırız.
 *
 *  attr(): Bu methodu çağıran elementin, belirtilen bir attribute'unu return eder.
 *
 *         Örneğin:
 *         <book id="12345">
             <title>The Catcher in the Rye</title>
             <author>J.D. Salinger</author>
             <genre>Novel</genre>
             <price currency="USD">10.99</price>
            </book>

            Aşağıdaki kodlar ile id ve currency attribute'lerini alabiliriz.
            Element bookElement = document.getElementsByTag("book")
            String id = bookElement.attr("id")
            Element priceElement = bookElement.getElementsByTag("price")
            String currency = priceElement.attr("currency");
 */

class XmlResult {

    fun xmlCatalog(): List<Plant> {
        val arr = mutableListOf<Plant>()
        val url = "https://www.w3schools.com/xml/plant_catalog.xml"
        val document: Document =
            Jsoup.connect(url).timeout(15000).ignoreContentType(true).get()
        val elements: Elements =
            document.getElementsByTag("PLANT")

        for (item in elements) {
            val common = item.getElementsByTag("COMMON").text()
            val botanical = item.getElementsByTag("BOTANICAL").text()
            val zone = item.getElementsByTag("ZONE").text()
            val light = item.getElementsByTag("LIGHT").text()
            val price = item.getElementsByTag("PRICE").text()
            val plant = Plant(common, botanical, zone, light, price)
            arr.add(plant)
        }
        return arr
    }

    fun xmlCurrency(): List<Currency> {
        val arr = mutableListOf<Currency>()
        val url = "https://www.tcmb.gov.tr/kurlar/today.xml"
        val document: Document =
            Jsoup.connect(url).timeout(30000).ignoreContentType(true).get()
        val elements = document.getElementsByTag("Currency")
        val rootElement = document.firstElementChild()
        val tarih = rootElement?.attr("Tarih")
        val date = rootElement?.attr("Date")
        val bulten_no = rootElement?.attr("Bulten_No")
        Log.d("mkmmkm", "Root: $rootElement")
        Log.d("mkmmkm", "Tarih: $tarih")
        Log.d("mkmmkm", "Date: $date")
        Log.d("mkmmkm", "Bulten_No: $bulten_no")
        for (item in elements) {
            val unit = item.getElementsByTag("Unit").text()
            val isim = item.getElementsByTag("Isim").text()
            val currencyName = item.getElementsByTag("CurrencyName").text()
            val forexBuying = item.getElementsByTag("ForexBuying").text()
            val forexSelling = item.getElementsByTag("ForexSelling").text()
            val bankNoteBuying = item.getElementsByTag("BankNoteBuying").text()
            val bankNoteSelling = item.getElementsByTag("BankNoteSelling").text()
            val crossRateUSD = item.getElementsByTag("CrossRateUSD").text()
            val crossRateOrder = item.getElementsByTag("CrossRateOther").text()
            val currency = Currency(
                unit, isim, currencyName, forexBuying, forexSelling,
                bankNoteBuying, bankNoteSelling, crossRateUSD, crossRateOrder
            )
            arr.add(currency)
        }
        return arr
    }

}