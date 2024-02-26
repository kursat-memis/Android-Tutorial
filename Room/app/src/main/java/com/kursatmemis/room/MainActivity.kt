package com.kursatmemis.room

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.room.Room
import com.kursatmemis.room.configs.AppDataBase
import com.kursatmemis.room.dao.StudentDao
import com.kursatmemis.room.models.Student

/**
 * Üç Katmandan Oluşur:
 * 1. Entity: DB'deki tabloları temsil eden DataClass. Room, her entity için bir tablo oluşturur ve
 *    sınıfta bulunan alanlar tablodaki sütunlara karşılık gelir.
 *
 * 2. Dao: Veritabanı için belirttiğiz SQL sorgularını içeren bir interface'dir.
 *
 * 3. Database: Veritabanının oluşmasını sağlayan ana bileşendir. Bu abstract class sayesinde
 *    Dao'ya erişerek veritabanı üzerinde yapılması istediğimiz sorguları/işlemleri temsil eden
 *    methodlara erişebiliyoruz.
 */

/**
 * Nasıl Kullanılır?
 * 1- build.gradle(Module:app) dosyası içinde gerekli bağlantılar sağlanır.
 *  Aşağıdaki komut, android ve dependencies bloklarının arasına eklenir.
    apply plugin: "kotlin-kapt"

 *  Aşağıdaki komutlarda, dependencies kısmına eklenir.
    //Room
    implementation "androidx.room:room-runtime:2.4.1"
    kapt "androidx.room:room-compiler:2.4.1"
    //Coroutine
    implementation "androidx.room:room-ktx:2.3.0"
    implementation "androidx.lifecycle:lifecycle-runtime-ktx:2.3.1"

 *    Not: Komutlar eklendikten son sürümleri kontrol edilir. Eğer güncel sürüm var ise komutlar
 *    update edilerek build.gradle dosyası sync edilir.
 *
 * 2- Tablomuzu temsil edecek bir DataClass oluşturuyoruz.(Student) (Gerekli açıklamalar ilgili classta var.)
 * 3- Kullanmak istediğimiz SQL sorgularını belirttiğimiz Dao interface'i oluşturulur. (StudentDao)
 * 4- Veritabanını oluşturacağımız bir AbstractClass oluştururuz. Bu abstract class, RoomDataBase
 * class'ını extend etmelidir. (AppDataBase)
 * 5- MainActivity içerisinde, Room.databaseBuilder() methodu ile AppDataBase objesi oluşturulur.
 *
 * Room.databaseBuilder(context, Class, name) Params:
 * context: Android context.
 * Class: Hangi class'tan bir obje oluşturmak istediğimizi belirttiğimiz parametredir.
 * name: Oluşturulacak olan veritabanının adını belirttiğimiz parametredir.
 *
 * 'compileDebugJavaWithJavac' hatası çözümü:
 *  https://stackoverflow.com/questions/75480173/android-studio-build-error-compiledebugjavawithjavac-task-current-target-it-1
 *
 */

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = AppDataBase.getInstance(this)

        val run = Runnable {
            db.studentDao().deleteAllStudents()

            val student1 = Student(1, "student1", "1-B")
            val student2 = Student(2, "student2", "2-B")
            val student3 = Student(3, "student3", "3-B")
            val student4 = Student(4, "student4", "4-B")
            val student5 = Student(5, "student5", "5-B")
            // student8 objesi veritabanına eklendiğinde studentId değeri otomatik 1 artacak.
            val student8 = Student(null, "student8", "8-B")

            var eklenenElemanPrimaryKey = db.studentDao().insert(student1)
            Log.w("mKm - Room", "Eklenen eleman primary key: $eklenenElemanPrimaryKey")
            eklenenElemanPrimaryKey = db.studentDao().insert(student2)
            Log.w("mKm - Room", "Eklenen eleman primary key: $eklenenElemanPrimaryKey")
            val eklenenElemanlarinPrimayKeyleri = db.studentDao().insertMultiStudents(listOf(student3, student4, student5))
            Log.w("mKm - Room", "Eklenen eleman primary keyler: $eklenenElemanlarinPrimayKeyleri")

            var etkilenenSatirSayisi = db.studentDao().deleteStudent(student5)
            Log.w("mKm - Room", "Silme işleminde etkilenen satir sayisi: $etkilenenSatirSayisi")
            etkilenenSatirSayisi = db.studentDao().deleteTwoStudents(listOf(student4, student3))
            Log.w("mKm - Room", "Silme işleminde etkilenen satir sayisi: $etkilenenSatirSayisi")
            // Aşağıdaki kod ile id'si 1 olan satır güncellenir.
            etkilenenSatirSayisi = db.studentDao().update(Student(1, "Yeni", "Yeni"))
            Log.w("mKm - Room", "Güncelleme işleminde etkilenen satir sayisi: $etkilenenSatirSayisi")
            Log.w("mKm - Room", "Database: ${db.studentDao().getAllStudents()}")
        }
        Thread(run).start()

    }
}