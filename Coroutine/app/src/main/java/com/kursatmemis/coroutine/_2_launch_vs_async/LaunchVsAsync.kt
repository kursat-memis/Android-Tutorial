package com.kursatmemis.coroutine._2_launch_vs_async

import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * Launch vs Async:
 * Her ikiside mevcut thread'i bloklamadan Coroutine'leri başlatmak için kullanılır.
 *
 * -> 'Launch', geriye bir 'Job' objesi return eder ve Coroutine içinde yapılan işlemlerin sonucunu
 *    içinde barındırmaz. Ancak Job objesi üzerinden Coroutine'i handle edebiliriz.
 *    Örneğin onu'cancel' edebiliriz.
 * -> Fire and forget prensibiyle çalışır. Yani bir Coroutine'i başlat ancak bunun sonucuyla ilgilenme.
 *    Unut bu Coroutine'i...
 *
 * -> 'Async' ise geriye bir 'Deferred<T>' objesi return eder. Bu obje üzerinden 'await()' methodunu
 *    çağırabiliriz. Await() methodu, Coroutine'in içinde yapılan işlemlerin bir sonucunu return eder.
 *    Yani bir Coroutine'i başlatıp ardından bu Coroutine içinde gerçekleştirilen işlemlerin bir
 *    sonucunu almak istiyorsak, Coroutine'i 'Async' ile başlatmalıyız.
 *    Not: await() bir suspend function'dır. Bundan dolayı ya bir Coroutine içinden ya da başka bir
 *         Coroutine içinden çağrılmalıdır. Bu method, Coroutine içinde return ettiğimiz sonuç
 *         hazırlanana kadar Coroutine'i suspend edecektir.
 * -> Ayrıca 'Deferred<T>' objesi, 'Job' objesinin bir instance'ıdır. Bundan dolayı 'Deferred<T>'
 *    objesi üzerinden de cancel() methodunu çağırabiliriz.
 *
 * Launch ve Async'nin bir farkıda Exception durumlarında ortaya çıkar:
 * -> Eğer 'launch' kullanarak başlattığımız bir Coroutine'de exception meydana gelirse, program
 *    crash olur.
 * -> Eğer 'async' kullanarak başlattığımız bir Coroutine'de exception meydana gelirse, program
 *    crash olmaz ve exception'ı Deferred<T> objesinin içinde tutar. Biz bu objeyi kullanarak
 *    await() methodunu çağırıp, sonucu almak istediğimizde try-catch bloklarını kullanarak
 *    exception'ı handle edebiliriz.
 *
 * Not: İnternette böyle yazıyor ama denediğim de ikisinde de app crash oldu...
 *
 * Hangisini Kullanayım?
 * -> Eğer Coroutine'i başlattıktan sonra ondan herhangi bir sonuç almayacak isek -> launch.
 *    Örn: Server'a ya da bir file'a veri göndermek/yazmak gibi durumlar için.
 *
 * -> Eğer Coroutine'i başlattıktan sonra ondan herhangi bir sonuç almak istersek -> async
 * -> Ek olarak paralel olarak aynı anda iki iş yaptırmak istersek -> async
 *    [launch ile paralel işlem yapamıyoruz.]
 *
 * Not: Performans olarak launch ve async kullanmak hemen hemen aynı düzeyde olacaktır.
 *      Yani aralarında performans farkı yoktur denilebilir.
 *
 * Not: Async ve launch iç içe de kullanılabilir.
 */

fun main() {
    startCoroutineWithLaunch()
    startCoroutineWithAsync()
}

fun startCoroutineWithLaunch() {
    val job: Job
    runBlocking {
        job = launch {
            println("Coroutine started with launch...")
            delay(2000)
            println("Coroutine finished with launch...")
        }

        launch {
            println("Job is cancelling!!!")
            job.cancel()
        }
    }
}

fun startCoroutineWithAsync() {
    runBlocking {
        val defferred = async {
            println("Coroutine started with async...")
            delay(1000)
            println("Coroutine finished with async...")
            "My Return Value"
        }

        val returnValue = defferred.await()
        println("Return value: $returnValue")
    }
}

