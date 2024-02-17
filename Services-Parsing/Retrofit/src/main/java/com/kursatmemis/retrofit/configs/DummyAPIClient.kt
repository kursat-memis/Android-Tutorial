package com.kursatmemis.retrofit.configs

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * object DummyAPIClient: Bu bir singleton objedir. Bunun anlamı; bu obje bir kere oluşturulur
 *                        ve programın her yerinde aynı obje kullanılır.
 *
 * BASE_URL: İstek atacağımız sitenin temel url'i.
 *
 * Retrofit.Builder(): Retrofit objesini oluşturmak için gerekli olan method.
 *
 * baseUrl(BASE_URL): İstek atacağımız sitenin url'i belirlendi.
 *
 * addConverterFactory(GsonConverterFactory.create()): Response olarak gelen JSON yapısını
 * veriyi modellemek için kullandığımız Data Class tipine dönüştürme işlemini gerçekleştirecek
 * convertor'ı belirliyoruz.
 *
 * build(): Bu satırda, Retrofit nesnesi oluşturuluyor.
 *
 * getClient(): API isteklerinde bulunmamızı sağlayacak olan retrofit objesini return eder.
 * Retrofit objesinin null olup olmadığını kontrol ederek program boyunca sadece bir tane
 * retrofit objesinin oluşturulmasını ve aynı objenin kullanılmasını sağlıyoruz.
 *
 */


object DummyAPIClient {
    private val BASE_URL = "https://dummyjson.com/"
    private var retrofit: Retrofit? = null

    fun getClient() : Retrofit {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        return retrofit as Retrofit
    }

}