package com.kursatmemis.room.configs

import android.content.Context
import androidx.room.Database
import androidx.room.Room
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

    /** Singleton tasarım deseniyle veritabanı örneği oluşturma */

    /**
     * synchronized(this, {}):
     * İkinci parametre olarak aldığı lambda ifadesinin scope'unun aynı anda birden fazla thread
     * üzerinde yürütülmesini engeller.
     * Yani A thread'i üzerinden bu scope çalıştırılırken A thread'i bu scope'a bir kilit koyuyor
     * ve başka thread'ler üzerinden bu scope'un çalışması engelleniyor. Ne zaman ki A thread'i
     * bu scope'la işini bitirir, işte o zaman başka thread'ler bu scope'u çalıştırabilirler.
     * Biz burada synchronized'ı kullanarak aynı anda birden fazla thread'in bu scope'u çalıştırarak
     * birden fazla 'instance' oluşturmasını, yani birden fazla veritabanı örneğinin oluşturulmasını
     * engelliyoruz.
     *
     * @Volatile:
     * Normalde bir thread üzerinde bir değişkenin değerini değiştirdiğimizde diğer thread'ler,
     * bu değişkenin değerinin değiştiğini anında fark edemeyebiliyor.
     * [Çünkü her thread kendi önbelleğinde bu değişkenin kopyasıyla çalışıyor.]
     * Ama bu annotation ile işaretlenmiş bir değişkenin değeri bir thread üzerinde değişitirilirse,
     * diğer thread'ler de anında bu değişikliğin farkında olacaktır. Diyelim ki bizim
     * uygulamamızda 'instance' objesini, yani veritabanı objesini aynı anda kullanan A ve B
     * thread'leri var ve bu 'instance' objesinin ilk başta değeri null olsun. Eğer A thread'inde
     * bu objeye bir değer atanırsa, B thread'i bu değişikliği anında fark eder ve artık bu
     * objeyi null olarak değil, değeri atanmış halde kullanır. Bu sayede, B thread'i gidip tekrar
     * bu objeyi yeniden oluşturmaz ve dolayısıyla bellekte birden fazla veritabanı objesinin
     * aynı anda olmaması sağlanır.
     */

    companion object {
        @Volatile
        private var instance: AppDataBase? = null

        fun getInstance(context: Context): AppDataBase {
            return instance ?: synchronized(this) {
                val newInstance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java,
                    "app_database"
                ).build()
                instance = newInstance
                newInstance
            }
        }
    }

    abstract fun studentDao(): StudentDao
}