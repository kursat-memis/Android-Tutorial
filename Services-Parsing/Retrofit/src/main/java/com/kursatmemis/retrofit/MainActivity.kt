package com.kursatmemis.retrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.kursatmemis.retrofit.configs.DummyAPIClient
import com.kursatmemis.retrofit.models.Product
import com.kursatmemis.retrofit.models.ProductResponse
import com.kursatmemis.retrofit.models.User
import com.kursatmemis.retrofit.services.DummyService
import com.kursatmemis.retrofit.services.LoginInfo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// Not: ProductResponse-DummyAPIClient-DummyService içinde gerekli açıklamalar var.
// Not: Retrofit ile Coroutine - RxJava kullanmak daha temiz kod ve maliyetten kazanç sağlar.
// Not: Senkron istekler için -> execute(), Asenkron istekler için -> enqueue()
//      Senkron: Bir işlem bitmeden diğerine geçilmez.
//      Asenkron: İşlemin tamamlanmasını beklemeden diğer işleme geçilir.

/**
 * IMPORTANT NOTE:
 * Eğer, büyük verilerle uğraşıyorsak, bu verilerin internetten getirilmesi sırasında kullanıcı
 * farklı bir Activity'e geçiş yapsa bile bu veriler arka planda getirilmeye devam edecektir.
 * Bu da uygulamamızın performansını düşürür. Bundan dolayı Acitivity'nin Life Cycle methodlarını
 * kullanarak, Retrofit'in 'Call' çağrılarını cancel() methodu üzerinden iptal edebiliriz. Bu sayede
 * bellek daha performanslı hale gelir.
 */

/**
 * Retrofit Component'leri:
 * 1. Data Class:
 *    Sunucudan gelen JSON veya XML formatındaki response'u, kotlinde bir obje olarak kullanmamız
 *    için oluşturduğumuz model.
 *
 * 2. Interface:
 *    URL manipülasyonu yaptığımız ve API'e erişmek için kullanılması gereken
 *    methodların tanımlarının yapıldığı interface'dir.
 *
 *    Bu metotların her biri, sunucuya gönderilecek isteği, isteğin yapısını
 *    ve sunucudan dönecek yanıtın yapısını tanımlar.
 *
 * 3. Object: RESTful API'ye bağlanmak için retrofit objesi oluşturduğumuz katmandır.
 *
 */

/** Retrofit Nasıl Kullanılır?
1. Network isteği için gerekli olan internet izni Manifest.xml'de verilir.
<uses-permission android:name="android.permission.INTERNET"/>

2. Retrofit ve GSON dönüştürücü kütüphaneler, gradle'da uygulamaya eklenir.
Retrofit: https://mvnrepository.com/artifact/com.squareup.retrofit2/retrofit
GSON: https://mvnrepository.com/artifact/com.squareup.retrofit2/converter-gson
Not: İkisininde aynı sürümde olması gerekiyor.

IMPORTANT NOTE:
GSON Kütüphanesi: Bu kütüphane Google tarafından geliştirilen ve internetten çekilen JSON
formatındaki verileri, Kotlin objelerine dönüştürme işlemini gerçekleştiren
kütüphanedir. Eğer ki retrofit ile internetten JSON formatında değil de XML
formatında veriler çekiyorsak bu durumda GSON yerine XML dönüştürücü
kütüphaneler kullanmalıyız. (SimpleXML vb.)

3. Gelecek olan response'un bir kotlin objesi olarak tutulması için bir data class
kurulur.
Not: Response'daki ve data class'daki variable isimlerinin aynı olması gerekiyor.
Eğer data class'ımızda, variable ismini farklı vermek istiyorsak o variable'ı
tanımlamadan önce @SerializedName("responstaki değişkenin adı") annotation'ını
eklemeliyiz.

4. Retrofit'in yapması gereken işleri tanımadlığımız methodlar ve hangi url'de bu
işlemleri gerçekleştirmesi gerektiğini bildiren annotation'lardan oluşan
interface yazılır. (Interface Adı = Site Adı + Service)

5. getClient() methodunu çağırarak return edilecek retrofit üzerinden create() methodunu çağırıp
DummyService interface'inin retrofit tarafından implement edilip bunun bir objesinin return
edilmesini sağlarız. Ardından bu obje üzerinden, DummyService içinde tanımladığımız methodları
çağırarak isteklerimizi sunucuya iletiriz.
 */

/**
 * HTTP STATUS CODES:
 * Retrofit ile bir sunucuya istek attığımızda, bize dönen response üzerinden code() methodunu
 * çağırarak status kodu alabilir ve isteğin durumunu bu koda göre yorumlayabiliriz.
 *
 * En çok karışılan kodlar ve anlamları aşağıdaki gibidir. Bunlardan farklı bir kod alındığında
 * internetten araştırılıp anlamı öğrenilebilir.
 *
 * 102: Sunucu, isteği işliyor ancak henüz yanıt göndermedi.
 * 200: İstek başarıyla işlendi ve sonuç return edildi.
 * 201: Yeni bir kaynak başarıyla oluşturuldu.
 * 202: İstek kabul edildi ancak işlenmesi henüz tamamlanmadı.
 * 400: Yanlış biçimlendirilmiş sözdizimi nedeniyle istek sunucu tarafından anlaşılamadı.
 *      İstemci, talebi değişiklik yapmadan TEKRARLAMAMALIDIR.
 * 401: İstek, kullanıcı kimlik doğrulaması gerektiriyor.
 * 404 Bulunamadı: İstek yapılan kaynak sunucuda bulunamadı.
 *
 */

class MainActivity : AppCompatActivity() {

    private lateinit var call: Call<ProductResponse>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /**
        val service = DummyAPIClient.getClient().create(UserService::class.java):
        Retrofit'in yapacağı işlemleri ve hangi linkte yapması gerektiğini belirlediğimiz
        DummyService interface'i implement eden bir obje return eder.
        (DummyService interface'inin implement edilmesi, Retrofit tarafından gerçekleştiriliyor.)

        dummyService.getProducts: Kendi yazdığımız DummyService interface'indeki
        getProducts methodunu çağırarak ürünlerin getirilmesi için sunucuya istek atıyoruz.

        enqueue(): İsteğin asenkron olarak yürütülmesini sağlar.

        object : Callback<List<UserModel>>: Burada bir Callback nesnesi yaratıyoruz.
        Bu, istek tamamlandığında, başarılı bir şekilde yanıt alındığında veya
        bir hata oluştuğunda çağrılacak metotların tanımlandığı bir arayüzdür.

        onResponse(): Sunucudan bir yanıt alındığında çağrılır ve
        Response nesnesi içinde sunucudan gelen verileri içerir.

        response.body(): Burada sunucudan alınan yanıt bize kotlin objesi olarak return edilir.

        onFailure(): İstek tamamlanırken bir hata oluştuğunda çağrılır.
         */

        val dummyService = DummyAPIClient.getClient().create(DummyService::class.java)
        call = dummyService.getProductsWithEndPoint()

        /** Asenkron Olarak İstek Atılması */

        call.enqueue(object : Callback<ProductResponse> {
            override fun onResponse(
                call: Call<ProductResponse>,
                response: Response<ProductResponse>
            ) {
                val productResponse = response.body()
                val statusCode = response.code()
                Log.w("mKm - dummy - products", "Status Code: $statusCode")

                productResponse?.let {
                    val productList = it.products

                    for (product in productList) {
                        Log.w("mKm - dummy - products", "ID: ${product.id}")
                        Log.w("mKm - dummy - products", "Title: ${product.title}")
                    }
                }

                if (productResponse == null) {
                    Log.w("mKm - dummy - products", "Response is null!")
                }

            }

            override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                Log.w("mKm - dummy - products", "onFailure: $t")
            }

        })

        ////////////////////////////////////////////////////////////////////////////////////7
        ////////////////////////////////////////////////////////////////////////////////////7

        /** Senkron Olarak İstek Atılması */

        // Android, Main Thread'de Network işlemleri yapılmasına musade etmez.
        // Bu yüzden bunu ayrı bir thread'de yapıyoruz.
        /*Thread{
            val response = call.execute() // İşlem tamamlanana kadar alt satırlara geçmez.
            val statusCode = response.code()
            Log.w("mKm - krst", "Status Code: $statusCode")
            val responseBody = response.body()
            responseBody?.let {
                val productList = it.products
                for (product in productList) {
                    Log.w("mKm - krst", "Product: ${product.title}")
                }
            }
            Log.w("mKm - krst", "Here...")
        }.start()*/

    }

    override fun onPause() {
        super.onPause()
        call.cancel()
    }

}