package com.kursatmemis.retrofit.services

import com.kursatmemis.retrofit.models.UserModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

/**
 * @GET("comments"):
 * Atılacak olan isteğin türünün GET olduğunu ve BaseUrl'e eklenecek olan
 * endpoint'in 'comments' olması gerektiğini belirttir.
 * Yani bu satırda;
 * GET = Veri istediğimiz anlamına,
 *
 * 'comments' = BaseURL'i, 'https://jsonplaceholder.typicode.com/' olan adresin sonuna 'comments'
 *              ekleyerek verilerin çekileceği adresin
 *              'https://jsonplaceholder.typicode.com/comments' olması gerektiğini belirttir.
 *              Yani ben baseurl'in, comments uzantısından verileceği çekeceğim demektir.
 *
 * callGetComments():
 * Bu methodun içeriği retrofit tarafından doldurulacaktır.
 * Bu method sayesinde, base url + comments linki için sunucuya bir get isteği atmış oluruz.
 * Bu methodun geri dönüş tipi Retrofit kütüphanesinde yazılmış olan Call interface'inin
 * bir objesi olmalı.
 *
 * Call<x>: x, burada dönecek olan cevabın tipini belirtiyor.
 *
 * List<UserModel>: Respons, '[' ifadesiyle başladığı için bir list döndereceğini anladığımızdan
 *                  dolayı, List tanımladık. Responstaki '[' ifadesinin içindeki objelerin,
 *                  kendi yazdığımız UserModel data class'ı tipindeki objeler olmasını istediğimiz
 *                  için, <UserModel> yazdık.
 */

/**
 * Kısaca: Kısaca bu fonksiyon aracılığıyla servisten belirttiğimiz url’deki verileri
 * çek demiş oluyoruz.
 * Çekilen verileri oluşturduğumuz Call<List<UserModel>> objesiyle retrun ediyoruz.
 */

/**
 * Retrofit Metotları:
 * GET: Sunucuya, bize veri vermesi için istek atan ve bir response alan metottur.
 *
 * POST: Sunucuda bir obje yaratmak için kullanılan metottur.
 *       Not: POST methodunda, sunucuya giden
 *       veri, body içinde gizlenir.
 *
 * PUT: Sunucudaki bir verinin güncellenmesi için kullanılır.
 *      Not: Peki bir objenin bir alt objesini değiştirmek istersek?
 *      O zaman put bunu önemsemez gelen veri ile elindeki verinin ne olduğuna bakmadan
 *      sunucudaki objeyi tümüyle son haline günceller. Büyük nesneler için kullanışsızdır.
 *
 * DELETE: Sunucudaki bir objeyi silen method.
 *
 * OPTIONS: O an için sunucunun yanıt verebileceği methodları bize verir.
 *
 * HEAD: Bu metot sayesinde istek atılmadan kaynağın boyutunu geçerliliğini,
 * erişilebilirliğini gibi yapıları öğrenebiliriz.
 *
 * PATCH: Sunucudaki bir objenin, herhangi bir özelliğini değiştirebilen method.
 * PUT methodunun, büyük objeler için kullanışsızlığından dolayı bu methodu kullanırız.
 */

/**
 * Queries:
 * Sunucuya yollayacağımız istekleri daha özel hale getirebilmek için Queries'ları kullanırız.
 *
 */

interface UserService {
    @GET("comments")
    fun getComments(): Call<List<UserModel>>

    /**
     * Aşağıdaki GET annotation'ına, tüm link verilmiştir. Ama yukarıdaki gibi endpoint kullanmak
     * daha yaygındır.*/
    /*
    @GET("https://jsonplaceholder.typicode.com/comments")
    fun getComments() : Call<List<UserModel>>
    */

    /**
     * Aşağıdaki GET annotation'ına, herhangi bir link verilmemiştir.
     * Hangi siteden veri çekileceği, getComments methoduna argüman olarak gönderilecek olan
     * link'e göre karar verilecektir.
     */
    @GET
    fun getComments(@Url url: String): Call<List<UserModel>>


    /**
     * Aşağıdaki method'a parametre olarak 2 gönderirsek, sadece postId'si, 2'ye eşit olan
     * verilerin bize getirilmesini sağlarız.
     * Not: JSON Formatındaki parametre adıyla, Query içindeki parametre adı aynı olmalıdır. Ancak
     * fonksiyonu bizim geçtiğimiz parametre adı farklı olabilir.
     */
    @GET("comments")
    fun getSpecialComments(@Query("postId") postId: Int): Call<List<UserModel>>

    /**
     * Aşağıdaki method ile hem endpoint'i argüman olarak yollayabilir hem de query'imizi
     * sunucuya gönderebiliriz.
     * Not: Süslü parantez içindeki değişken ile “@Path” annotation’ı içindeki değişkenlerinin
     * isimlerinin aynı olması gerekmektedir.!
     */

    @GET("{endpoint}")
    fun getSpecialComments(@Path("endpoint") endpoint: String, @Query("postId") postId: Int):
            Call<List<UserModel>>

}