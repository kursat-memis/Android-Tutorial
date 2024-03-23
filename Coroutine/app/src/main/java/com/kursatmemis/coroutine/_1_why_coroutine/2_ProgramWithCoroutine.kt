package com.kursatmemis.coroutine._1_why_coroutine

import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Coroutine'i neden kullanmamız gerektiğine dair harika bir yazı:
 * https://kt.academy/article/cc-why#coroutines-on-android--and-other-frontend-platforms-
 */

class MainActivityWithCoroutine : AppCompatActivity() {
    fun main() {

        /**
         * Coroutine'den kullanmadığımız koda kıyasla, Coroutine kullanarak aynı işi yaptığımız
         * bu kod diğerine göre çok daha temiz!!!
         *
         * Tıpkı senkron bir kod yazar gibi kod yazıyoruz ancak bu kod aslında arka planda
         * asenkron olarak çalışıyor... İşte bu Coroutine'in bize sağladığı çok güzel bir özellik!
         */

        GlobalScope.launch(Dispatchers.Main) {
            fetchAndShowUser()
        }
    }

    private suspend fun fetchAndShowUser() {
        val user = fetchUser() // fetch User on IO Thread
        showUser(user) // show User on UI Thread
    }

    private suspend fun fetchUser(): User {
        val user = withContext(Dispatchers.IO) {
            // make network call and return the user
            User(1, "User - 1")
        }

        return user
    }


    private fun showUser(user: User) {
        // Show the user on the screen.
    }

}