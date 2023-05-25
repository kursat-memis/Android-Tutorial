package com.kursatmemis.retrofit.configs

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * BASE_URL: İstek atacağımız sitenin temel url'i.
 *
 * Retrofit.Builder(): Diğer methodları kullanabilmek için oluşturduğumuz bir obje.
 *
 * baseUrl(BASE_URL): İstek atacağımız sitenin url'i belirlendi.
 *
 * addConverterFactory(GsonConverterFactory.create()): Response olarak gelen JSON yapısını
 * serileştirir ve gelen verinin bizim yazdığımız Data Class tipine dönüşmesini sağlar.
 *
 * build(): Bu satırda, Retrofit nesnesi oluşturuluyor.
 *
 * .create(UserService::class.java): Bu satırda, Retrofit nesnesi kullanarak bizim yazdığımız
 * UserService interface'ini implement eden bir sınıfın objesi oluşturuluyor.
 *
 * getClient(): API isteklerinde bulunmamızı sağlayacak olan retrofit objesini return eder.
 * Retrofit objesinin null olup olmadığını kontrol ederek program boyunca sadece bir tane
 * retrofit objesinin oluşturulmasını sağlıyoruz.
 *
 */

    object UserApiClient {
        private val BASE_URL = "https://jsonplaceholder.typicode.com/" // Sonu '/' ile bitmeli.
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