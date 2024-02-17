package com.kursatmemis.retrofit.services

import com.kursatmemis.retrofit.models.Product
import com.kursatmemis.retrofit.models.ProductResponse
import com.kursatmemis.retrofit.models.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

/**
 * Retrofit Metotları:
 * GET: Sunucuya, bize veri vermesi için istek atan ve bir response alan metottur.
 *
 *   Not: POST-PUT-PATCH methodlarında, sunucuya gönderilecek veri, body annotation'ı ile
 *        işaretlenmelidir.
 *        Bu annotation sayesinde Retrofit, bu verinin sunucuya eklenmesi gereken veri olduğunu
 *        bilir ve Kotlin objesini otomatik olarak Json objesine çevirerek sunucuya ekler.
 *        Eğer bu annotation kullanılmaz ise; Retrofit, bu objenin sunucuya eklenmek istenen
 *        obje olup olmadığını bilemez ve hata fırlatır.
 *
 * POST: Sunucuda bir obje yaratmak için kullanılan metottur.
 *
 * PUT: Sunucudaki bir verinin güncellenmesi için kullanılır.
 *      Sunucudaki verinin tüm alanlarını günceller. Eğer argüman olarak aldığı objenin alanları,
 *      sunucudaki verinin alanlarının hepsini belirtmiyorsa, belirtilmeyen alanlar null olarak
 *      geçilir.
 *
 * PATCH: Sunucudaki bir objenin, tamamını değilde herhangi bir özelliğini değiştirebilen method.
 *        PUT methodunun, büyük objeler için kullanışsızlığından dolayı bu methodu kullanırız.
 *
 * PUT vs PATCH:
 * Diyelim ki sunucudaki verimizde; "ID", "email", "username" alanlarını tutuyoruz. PATCH methodu
 * ile sadece id'yi değiştirebiliriz. Ancak put methodu ile sadece id'yi değiştirmek istediğimizde
 * belirtmediğimiz email ve username alanları null olarak atanacaktır.
 *
 * DELETE: Sunucudaki bir objeyi silen method.
 *
 * OPTIONS: O an için sunucunun yanıt verebileceği methodları bize verir.
 *
 * HEADERS: Sunucuya istek atarken ek bilgiler de yollamamıza olanak sağlar.
 *          Örneğin:
 *          -> İsteğin veya yanıtın gövdesinin hangi formatta olduğunu belirtir. (json, text vb.)
 *          -> İsteğin veya yanıtın dilini belirtir. (TR, EN vb.)
 *          -> Token bilgisini göndermek. Bu sayede sunucunun token'in geçerli olup olmadığını
 *             kontrol etmesini sağlama.
 *
 *          Bu şekilde header kullanarak sunucuya ek bilgiler sağlar ve daha açıklayıcı yanıtlar
 *          alabiliriz. Header kullanmadan da istek gönderebiliriz ancak bu durumda istek gövdesinin
 *          formatını vb. bilmeyebilir ve bu da hatalara yol açabilir.
 *
 */

/**
 * Queries:
 * Sunucuya yollayacağımız istekleri daha özel hale getirebilmek için Query'leri kullanırız.
 * Aşağıda örnekleri verilmiştir.
 */

/**
 * Call<T>:
 * Retrofit, sunucuya istek göndermek için Call objelerini kullanır. Bu yüzden istek methodlarının
 * tipleri 'Call' olarak belirlenmelidir. Bu Call objeleri kullanılarak, asenkron-senkron bir şekilde
 * istek atabilir veya istekleri iptal edebiliriz.
 */
interface DummyService {

    @GET("products")
    fun getProductsWithEndPoint(): Call<ProductResponse>

    /**
     * Aşağıdaki GET annotation'ına, tüm URL verilmiştir. Ama yukarıdaki gibi endpoint kullanmak
     * daha yaygındır.
     */
    @GET("https://dummyjson.com/products")
    fun getProductsWithoutArgs(): Call<ProductResponse>

    /**
     * Aşağıdaki GET annotation'ına, herhangi bir url/endpoint verilmemiştir.
     * Hangi siteden veri çekileceği, getProductsWithArgs methoduna argüman olarak gönderilecek olan
     * String Url'e göre karar verilecektir.
     */
    @GET
    fun getProductsWithArgs(@Url url: String = "https://dummyjson.com/products"): Call<ProductResponse>

    /**
     * Query annotation'ı, sorgu yapmamıza olanak sağlar.
     * Örneğin; "https://dummyjson.com/products/search?q=phone" gibi bir url'den veri alacak olalım.
     * URL'in tamamını 'GET' annotation'ında belirtip statik bir method yazmak yerine 'searchProduct'
     * methodunda olduğu gibi 'Query' annotation'ınını kullanarak dinamik bir method yazıp
     * sorgu yapabilirim.
     *
     * Not: URL'deki sorgu parametresi adıyla, Query içindeki parametre adı aynı olmalıdır. Ancak
     * fonksiyona bizim geçtiğimiz parametre adı farklı olabilir.
     *
     * Not: Eğer sorgulanacak birden çok parametre varsa, bu parametreleri de Query annotation'ları
     * kullanarak methodun parametresinde belirtebiliriz.
     */
    @GET("products/search")
    fun searchProduct(@Query("q") s: String = "furniture"): Call<ProductResponse>

    /**
     * Aşağıdaki method ile hem endpoint'i argüman olarak yollayabilir hem de query'imizi
     * sunucuya gönderebiliriz.
     * Not: Süslü parantez içindeki değişken ile “@Path” annotation’ı içindeki değişkenlerinin
     * isimlerinin aynı olması gerekmektedir.!
     */
    @GET("{endpoint}")
    fun searchProduct(@Path("endpoint") endpoint: String, @Query("q") s: String = "furniture"): Call<ProductResponse>

    @POST("products/add")
    fun createProduct(@Body product: Product): Call<Product>

    @PUT("products/1")
    fun updateProductWithPut(@Body product: Info = Info("newTitle")): Call<Product>

    @PATCH("products/1")
    fun updateProductWithPatch(@Body product: Info = Info("newTitle")): Call<Product>

    @DELETE("products/1")
    fun deleteProduct(): Call<Product>

    @Headers("Content-Type: application/json")
    @POST("auth/login")
    fun getToken(@Body loginInfo: LoginInfo) : Call<User>
}

data class Info(val title: String)

data class LoginInfo(val username: String, val password: String)