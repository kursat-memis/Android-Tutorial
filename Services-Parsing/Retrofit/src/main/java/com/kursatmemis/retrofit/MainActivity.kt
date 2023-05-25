package com.kursatmemis.retrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.kursatmemis.retrofit.configs.CoinsApiClient
import com.kursatmemis.retrofit.configs.UserApiClient
import com.kursatmemis.retrofit.models.CoinsModel
import com.kursatmemis.retrofit.models.UserModel
import com.kursatmemis.retrofit.services.CoinsService
import com.kursatmemis.retrofit.services.UserService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Retrofit Nedir?
 * Retrofit bir kütüphanedir. Web sayfalarından JSON veya XML gibi fotmatlardaki
 * verileri çekmemizi sağlayan bir kütüphanedir.

 * Bu kütüphane, Restful web servislerine yapılan isteklerini kolay bir şekilde
 * yönetmeyi sağlar.
 * Retrofit ile Json Parsing, Gson ile kolayca yapılabilir.

 * Retrofit bir rest istemcisidir
 * Rest İstemcisi: Bir RESTful API'ye HTTP protokolü üzerinden istekler göndermek ve
 * sunucudan yanıt almak için kullanılan bir istemcidir.
 *
 * RESTful API: REST mimarisini kullanan servislere denir.
 *
 * REST: Client(İstemci)-server arasındaki haberleşmeyi sağlayan HTTP protokolü üzerinden
 * çalışan bir mimaridir. İstemci ve sunucu arasında XML ve JSON verilerini taşıyarak
 * uygulamanın haberleşmesini sağlar.
 *
 * API (Application Programming Interface):
 * Yazılım uygulamalarının birbirleriyle iletişim kurmalarını sağlayan bir arayüzdür.
 * Uygulamaların veya cihazların birbirine nasıl bağlanabileceğini ve birbirleriyle iletişim
 * kurabileceğini tanımlayan bir dizi kuraldır.
 *
 * REST ile yazılmış bir servisle çalışmak için ihtiyacımız olan tek şey URL.
 * Bir URL’e istek attığımızda, URL size JSON veya XML formatında bir cevap döndürür,
 * dönen cevap parse edilir ve servis entegrasyonunuz tamamlanır.

 */

/**
 * Retrofit Avatajları:
 * 1. Kolay Kullanım: RESTful API'lere erişmek ve verileri almak veya göndermek için
 * gereken kod miktarı azdır.
 *
 * 2. Caching: Retrofit, verileri önbelleğe alarak uygulamanın daha hızlı çalışmasını sağlar.
 * Bu sayede, aynı verileri birden fazla kez yüklemek yerine, önbelleğe alınan verileri
 * kullanabilirsiniz.
 *
 * 3. Hız: Retrofit çok hızlı bir network kütüphanesidir.
 */

/**
 * Retrofit Component'leri:
 * 1. Data Class:
 *    Sunucudan gelen JSON veya XML formatındaki response, kotlinde kullanabileceğimiz
 *    bir obje olması için, bizim oluşturduğumuz Data Class'ın objesine dönüştürülür.
 *    (JSON formatındaki verinin, bizim oluşturduğumuz data class tipinde bir objeye
 *    dönüştürülmesi işlemi, retrofit tarafından otomatik olarak yapılır.)
 *
 * 2. Interface:
 *    URL manipülasyonu yaptığımız ve API'e erişmek için kullanılması gereken
 *    methodların tanımlarının yapıldığı interface'dir.
 *
 *    Bu metotların her biri, sunucuya gönderilecek isteği, isteğin yapısını
 *    ve sunucudan dönecek yanıtın yapısını tanımlar.
 *
 * 3. Object: RESTful API'ye bağlanmak için oluşturduğumuz object'dir.
 *
 */


// Not: UserModel-UserApiClient-UserService içinde gerekli açıklamalar var.

/** Retrofit Nasıl Kullanılır?
1. Network isteği için gerekli olan internet izni Manifest.xml'de verilir.

2. Retrofit ve GSON dönüştürücü kütüphaneler, gradle'da uygulamaya eklenir.
Retrofit: https://mvnrepository.com/artifact/com.squareup.retrofit2/retrofit
GSON: https://mvnrepository.com/artifact/com.squareup.retrofit2/converter-gson
Not: İkisininde aynı sürümde olması gerekiyor.

3. Gelecek olan response'un bir kotlin objesi olarak tutulması için bir data class
kurulur.
Not: Response'daki ve data class'daki variable isimlerinin aynı olması gerekiyor.
Eğer data class'ımızda, variable ismini farklı vermek istiyorsak o variable'ı
tanımlamadan önce @SerializedName("responstaki değişkenin adı") annotation'ını
eklemeliyiz.

4. Retrofit'in yapması gereken işleri tanımadlığımız methodlar ve hangi url'de bu
işlemleri gerçekleştirmesi gerektiğini bildiren annotation'lardan oluşan
interface yazılır. (Interface Adı = Site Adı + Service)

5. İçerisinde Retrofit objesini oluşturacağımız bir obje oluşturulur.
 */

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /**
        val service = UserApiClient.getClient().create(UserService::class.java):
        Retrofit'in yapacağı işlemleri ve hangi linkte yapması gerektiğini belirlediğimiz
        interface'i implement eden bir obje return eder. Ve o obje, service adındaki
        değişkene aktarılır.

        service.callGetComments: Kendi yazdığımız UserService interface'indeki
        callGetComments methodunu çağırarak Sunucudan List<UserModel> almak için bir istek
        gönderir.

        enqueue(): İsteğin asenkron olarak yürütülmesini sağlar.
        Bu yöntemin bir Callback nesnesi bekleyen bir parametresi vardır.

        object : Callback<List<UserModel>>: Burada bir Callback nesnesi yaratıyoruz.
        Bu, istek tamamlandığında, başarılı bir şekilde yanıt alındığında veya
        bir hata oluştuğunda çağrılacak metotların tanımlandığı bir arayüzdür.

        onResponse(): Sunucudan bir yanıt alındığında çağrılır ve
        Response nesnesi içinde sunucudan gelen verileri içerir.
        Burada aldığımız yanıtı body() yöntemiyle işlenecek verileri içerdiğine dikkat edin.

        response.body(): Burada sunucudan alınan yanıt bize kotlin objesi olarak return edilir.

        onFailure(): İstek tamamlanırken bir hata oluştuğunda çağrılır.
         */

        val userService = UserApiClient.getClient().create(UserService::class.java)
        userService.getComments().enqueue((object : Callback<List<UserModel>> {
            override fun onResponse(
                call: Call<List<UserModel>>,
                response: Response<List<UserModel>>
            ) {
                Log.d("Retrofit: ", "onResponse")
                val comments: List<UserModel>? = response.body()

                if (comments != null) {
                    for (item in comments) {
                        Log.d("Comment-PostId: ", "${item.postId}")
                        Log.d("Comment-Name: ", "${item.id}")
                        Log.d("Comment-Name: ", item.name)
                        Log.d("Comment-EMail: ", item.email)
                        Log.d("Comment-Comment: ", item.comment)
                    }
                }
            }

            override fun onFailure(call: Call<List<UserModel>>, t: Throwable) {
                Log.d("Retrofit: ", "onFailureUser: ${t.toString()}")
            }
        }))

        userService.getSpecialComments(2).enqueue(object : Callback<List<UserModel>> {
            override fun onResponse(
                call: Call<List<UserModel>>,
                response: Response<List<UserModel>>
            ) {
                var body = response.body()

                if (body != null) {
                    Log.d("Special-Comments: ", "Size: ${body.size}")
                    for (item in body) {
                        Log.d("Special-Comments: ", "Email: ${item.email} " +
                                "postId: ${item.postId}")
                    }
                }
            }

            override fun onFailure(call: Call<List<UserModel>>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })

        userService.getSpecialComments("comments", 3)
            .enqueue(object : Callback<List<UserModel>>{
            override fun onResponse(
                call: Call<List<UserModel>>,
                response: Response<List<UserModel>>
            ) {
                var body = response.body()

                if (body != null) {
                    Log.d("Special-Comments-2-Param: ", "Size: ${body.size}")
                    for (item in body) {
                        Log.d("Special-Comments-2-Param: ", "Email: ${item.email} " +
                                "postId: ${item.postId}")
                    }
                }
            }

            override fun onFailure(call: Call<List<UserModel>>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })




        // Aşağıdaki kodlar farklı bir alıştırma.

        val coinService = CoinsApiClient.getClient().create(CoinsService::class.java)
        coinService.getCoins().enqueue(object : Callback<CoinsModel> {
            override fun onResponse(call: Call<CoinsModel>, response: Response<CoinsModel>) {
                var coinsModel = response.body()
                var coinsList = coinsModel?.data?.coins

                if (coinsList != null) {
                    for (coin in coinsList) {
                        Log.d("Coins - Name: ", "${coin.name}")
                        Log.d("Coins - Symbol: ", "${coin.symbol}")
                        Log.d("Coins - Price: ", "${coin.price}")
                    }
                }
            }

            override fun onFailure(call: Call<CoinsModel>, t: Throwable) {
                Log.d("Coins", "onFailure: ${t.toString()}")
            }
        })

    }
}