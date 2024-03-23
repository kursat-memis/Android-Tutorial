package com.kursatmemis.coroutine._3_with_context

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

/**
 * WithContext:
 * Bir suspend function'dır. Bundan dolayı ya bir Coroutine içinde ya da başka bir suspend function
 * içinde çağrılabilir.
 * Farklı bir dispatcher'a veya thread'e geçilmesini sağlar.
 * Yeni bir coroutine oluşturmaz. Sadece mevcut Coroutine'nin context'ini, yeni bir thread'e taşır.
 * Eğer istersek geriye bir sonuç return ettirebiliriz.
 */

fun main() {

    // Not: Bu bir sıradan kotlin dosyası olduğu için Dispatchers.Main kullandığımızda hata alırız.
    // Ancak aşağıdaki Dispatchers.Main kullanma sebebim Android'de ki kullanımını göstermektir.
    runBlocking(Dispatchers.Main) {
        val user = fetchUser()
        val imageURL = fetchProfilePhotoURL()
        showUserOnScreen(user, imageURL)
    }

    /**
     * Yukarıdaki kodda fetchUser() methodu çalışmasını tamamlamadan, fetchProfilePhotoURL() methodu
     * çalışmayacaktır. Ama aşağıdaki gibi async kullanırsak, fetchUser() ve fetchProfilePhotoURL()
     * methodları asenkron olarak aynı anda çalışırlar...
     */
    /*runBlocking {
        val user = async {
            fetchUser()
        }
        val imageURL = async {
            fetchProfilePhotoURL()
        }
        showUserOnScreen(user.await(), imageURL.await())
    }*/
}

suspend fun fetchUser() : User {
    return withContext(Dispatchers.IO) {
        // making a network call
        println("User Infos are Fetching!")
        delay(2000)
        println("User Infos Fetched!")
        User(1, "User-1")
    }
}

suspend fun fetchProfilePhotoURL(): String {
    return withContext(Dispatchers.IO) {
        // making a network call
        println("Profile Photo URL is Fetching!!")
        delay(2000)
        println("Profile Photo URL Fetched!")
        "www.imageurl.com"
    }
}

fun showUserOnScreen(user: User, photoURL: String) {
    // show user info and profile photo on the screen.
}

data class User(val id: Int, val name: String)