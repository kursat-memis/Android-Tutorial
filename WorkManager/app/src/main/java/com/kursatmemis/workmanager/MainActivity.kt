package com.kursatmemis.workmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.WorkRequest
import java.util.concurrent.TimeUnit

/**
 * Nasıl Kullanılır?
 * 1. Gerekli dependency build.gradle(module) dosyasına eklenilir.
implementation("androidx.work:work-runtime-ktx:2.8.0")
 * 2. Worker class'ını inherit eden bir class oluşturulur ve doWork() function'ı override edilir.
 *    (DatabaseUpdateWorker)
 * 3. Uygulama kapatılsa bile arka planda yapılmasını istenilen iş, doWork() function'ı içinde
 *    implement ettirilir.
 * 4. Bir WorkRequest objesi oluşturulur.
 *
 *    Not: WorkRequest, abstract bir sınıftır. Bu sınıfın isteği oluşturmak için kullanabileceğiniz
 *    iki türetilmiş uygulaması vardır: OneTimeWorkRequest ve PeriodicWorkRequest.
 *    Adlarından da anlaşılacağı gibi OneTimeWorkRequest tekrarlanmayan işleri planlamak için
 *    faydalıyken PeriodicWorkRequest belirli aralıklarla tekrarlanan işleri programlamak için
 *    daha uygundur.
 *
 * 5. Son olarak enqueue() function'ını kullanarak request, WorkManager'a gönderilir.
 */

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /** Worker class'ımıza göndereceğimiz data'nın ayarlanması */
        val data = Data.Builder().putInt("intKey", 1).build()

        /** Request'imiz için constraint belirleme */
        val constraint = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED) // Telefon yalnızca internete bağlı olduğu durumlarda çalışsın.
            .setRequiresCharging(true) // Telefonun yalnızca şarj edildiği durumlarda çalışsın.
            .build()

        /** WorkRequest objesi oluşturma */
        val databaseUpdateWorkRequest: WorkRequest
        databaseUpdateWorkRequest = OneTimeWorkRequestBuilder<DatabaseUpdateWorker>()
            .setInitialDelay(3, TimeUnit.SECONDS) // Request 3 saniye sonra gerçekleşsin.
            .setConstraints(constraint) // Constraint ataması
            .setInputData(data) // Worker class'ına gönderecemiz data
            .addTag("myTag") // Request'imizin iptali için kullanabileceğimiz tag.
            .build()


        /** WorkManager'a request gönderme.*/
        WorkManager.getInstance(this).enqueue(databaseUpdateWorkRequest)


        /** Periyodik olarak tekrarlanmasını istediğimiz request tanımlama */
        // Aşağıdaki request her 15 dk.'da bir gerçekleşecektir.
        /*val myWorkRequest: PeriodicWorkRequest =
            PeriodicWorkRequestBuilder<DatabaseUpdateWorker>(15, TimeUnit.MINUTES)
                .setConstraints(constraint) // Constraint ataması.
                .setInputData(data) // Worker class'ına gönderecemiz data
                .build()*/

        /** Request'in sonucunu dinleme */
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(databaseUpdateWorkRequest.id).observe(this
        ) {
            Log.w("mKm - krst", it.state.toString())
        }

        /** İstersek request'imizi iptal edebiliriz. */
        // WorkManager.getInstance(this).cancelAllWork() // Tüm request'leri iptal eder.
        // WorkManager.getInstance(this).cancelWorkById(databaseUpdateWorkRequest.id) // id'si verilen request'i iptal eder.


        /** Zincirleme ile birden fazla request'i peş peşe gerçekleştirebiliriz. */
        val oneTimeWorkRequest: OneTimeWorkRequest =
            OneTimeWorkRequestBuilder<DatabaseUpdateWorker>()
                //.setConstraints(constraint)
                .setInputData(data)
                .build()

        val oneTimeWorkRequest2: OneTimeWorkRequest =
            OneTimeWorkRequestBuilder<DatabaseUpdateWorker>()
                //.setConstraints(constraint)
                .setInputData(data)
                .build()

        val oneTimeWorkRequest3: OneTimeWorkRequest =
            OneTimeWorkRequestBuilder<DatabaseUpdateWorker>()
                //.setConstraints(constraint)
                .setInputData(data)
                .build()

        /*WorkManager.getInstance(this).beginWith(oneTimeWorkRequest)
            .then(oneTimeWorkRequest2)
            .then(oneTimeWorkRequest3)
            .enqueue()*/


    }

}