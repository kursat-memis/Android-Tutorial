package com.kursatmemis.room.configs

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kursatmemis.room.dao.StudentDao
import com.kursatmemis.room.models.Student

/**
 * @Database: Veritabınını oluşturacağımızı bildirdik.
 *            'entities' parametresine, tabloyu temsil eden DataClass'ımız gönderilir.
 *            'version' parametresine ise ilk oluşturduğumuzda 1 değeri verilir. Veritabanı
 *            şemasında herhangi bir değişiklik yaptığımızda (yeni sutun ekleme vb.) bu değeri
 *            arttırmalıyız ki veritabanımız güncellensin.
 *
 * studentDao(): Bu method sayesinde, StudentDao interface'inden bir obje bize return edilir.
 * Return edilen bu obje üzerinden, StudentDao interface'i içinde tanımladığımız sorgu methodlarını
 * kullanabiliriz.
 * Not: StudentDao interface'indeki methodların içeriği, Room Library tarafından doldurularak
 * implement ediliyor.
 */

@Database(entities = [Student::class], version = 1)
abstract class AppDataBase : RoomDatabase() {

    abstract fun studentDao(): StudentDao

    // Aşağıdaki kodu kullanmak performans olarak daha iyi olabilir.
    /*companion object {
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                val database = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "my-database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                instance = database
                database
            }
        }
    }*/

}