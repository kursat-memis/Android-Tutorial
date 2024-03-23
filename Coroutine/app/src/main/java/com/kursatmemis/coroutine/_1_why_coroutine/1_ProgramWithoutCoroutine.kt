package com.kursatmemis.coroutine._1_why_coroutine

import androidx.appcompat.app.AppCompatActivity

/**
 * Coroutine'i neden kullanmamız gerektiğine dair harika bir yazı:
 * https://kt.academy/article/cc-why#coroutines-on-android--and-other-frontend-platforms-
 */

class MainActivityWithoutCoroutine : AppCompatActivity() {
    fun main(){

        /**
         *
         * Not - 1: Android, Main Thread'de network çağrıları yapmamıza izin vermez.
         * Not - 2: Android, UI Thread dışında UI güncellemeleri yapmamıza izin vermez.
         *
         * Not-1'den dolayı, biz direkt olarak fetchAndShowUser() methodunu direkt çağırısak;
         * "NetworkOnMainThreadException" exception hatası alırız. Çünkü bu method içinde fetchUser()
         * methodu çağrılıyor ve bu method internetten veri çekmeye çalışıyor.
         *
         * Bundan dolayı fetchUser() methodunu farklı bir thread'de çalıştırmalıyız. Ardından bu methodun
         * return ettiği User objesini showUser() methoduna parametre olarak göndermeliyiz. Ancak
         * Not-2'den dolayı, UI işlemlerini yapan bu methodu UI Thread'de çağırmamız gerekiyor.
         *
         * İşte bu durumu çözmek için biz Coroutine'den önce thread'leri ve callbackleri
         * kullanırdık. Ancak bu yöntemlerde bazı sorunlarla karşılaşıyorduk:
         *
         * 1. Yeni bir thread oluşturmak bize bellek açısından masraf getiriyordu.
         * 2. Thread'leri iptal etmek kolay değildi. Hatta thread'i iptal etmek, tehlikeli
         * olabileceğinden ötürü tavsiye de edilmezdi. Bu yüzden kullanıcı bir Activity', destroy
         * edip başka activity'e geçiş yapsa bile thread arka planda çalışmaya devam ediyor ve
         * memory leak'e sebep veriyordu.
         * 3. Sık sık thread'ler arasında geçiş yapmak(mesela worker thread'den UI thread'e geçmek)
         * maliyetli ve kafa karıştırıcı oluyordu.
         * 4. Çok fazla callback kullanmak kafa karışıklığına sebebiyet veriyor ve kodun
         * okunabilirliğini zorlaştırıyordu ve çok fazla callback kullanmak asla tavsiye edilmeyen
         * bir durumdu...
         *
         * Thread ve Callback kullanımı yerine Android'ciler RxJava gibi teknolojilere geçiş
         * yaptılar. RxJava, memory leak'leri engelliyor ve task'leri cancel edebilme yeteneği
         * sağlıyordu. Ancak RxJava'nın karmaşık bir yapısı vardı ve Beginner developer'lar için
         * öğrenmesi çok kolay değildi.
         *
         * Bu sebeplere ek olarak Coroutine kullanmak için çok önemli bir sebep daha:
         * - Thread'leri kullanmak maliyetlidir. Çok fazla thread kullanımı uygulamamızın
         * OutOfMemoryException hatası alıp crash olmasına sebep olabilir. Oysa Coroutine kullanmak
         * neredeyse bedavadır...
         *
         * Bu yüzden çözüm Coroutine!!!
         *
         */

        fetchAndShowUser()

    }

    private fun fetchAndShowUser() {
        fetchUser() {user->
            showUser(user)
        }
    }

    private fun fetchUser(callback: (User) -> Unit) {
        var user: User
        val runnable = Runnable {
            // make network call and return the user
            user = User(1, "User - 1")

            // switch to UI Thread and run showUser()
            runOnUiThread{
                callback(user)
            }
        }
        val backgroundThread = Thread(runnable)
        backgroundThread.start()
    }


    private fun showUser(user: User) {
        // Show the user on the screen.
    }
}

data class User(val id: Int, val userName: String)