package com.kursatmemis.sqlite.database_helpers

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kursatmemis.sqlite.database_tables.NoteTable
import com.kursatmemis.sqlite.models.Note

/**
 * SQLiteOpenHelper Constructor Parametreleri:
 * 1. context: Context nesnesi.
 *
 * 2. name: Oluşturulacak olan veritabanının adını belirtir.
 *
 * 3. factory: Bu parametre, veritabanı bağlantısını oluşturmak için kullanılacak
 * CursorFactory nesnesini temsil eder. Bu parametre null olarak da belirtilebilir.
 *
 * 4. Bu parametre, oluşturulacak SQLite veritabanının sürüm numarasını belirtir.
 * Eğer veritabanı daha önce oluşturulduysa, yeni bir versiyon numarası belirtiltiğinde
 * onUpgrade() metodu çağrılır.
 */

/**
 * onCreate():
 * Uygulama yüklendiğinde sadece bir kez çağrılır.
 * Bu method ile uygulmada kullanılacak olan veritabanı ve bu veritabanında kullanılacak olan
 * tablolar oluşturulur.
 *
 * onUpgrade():
 * Uygulamanın veritabanı güncellendiyse (örneğin yeni bir sürüm geldiyse) çalışacak olan
 * metot'dur.
 */

class NoteDataBaseHelper(
    private val context: AppCompatActivity
) : SQLiteOpenHelper(context, NoteTable.DATABASE_NAME, null, NoteTable.VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(NoteTable.TABLE_NOTE_CREATE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS my_notes_table") // my_notes_table = tablo adı.
        onCreate(db)
    }

    /**
     * Veritabanına Veri Ekleme:
     * 1. SQLiteDataBase objesi oluşturulur.
     *    Bu obje, SQLiteOpenHelper class'ındaki getWritableDatabase metotu çağrılacak oluşturulur.
     *    Not: Veri ekleyeceğimiz için getWritableDatabase metodunu çağırıyoruz. Eğer veri okuyacak
     *    olsaydık getReadableDatabase metodunu çağırırdık.
     *
     * 2. Bir ContentValues objesi oluşturulur.
     *    Database'e eklemek istediğimiz verileri bu obje içine put methoduyla koyarız.
     *    cv.put(x, y):
     *    x: Key değeridir. Buradaki key değeri, tabloyu oluştururken belirttiğimiz sutun adı
     *    olmalıdır.
     *    y: Value değeridir. Key değeri olarak belirtilen sutune eklenmesini istediğimiz değerdir.
     *
     * 3. Birinci adımda oluşturduğumuz SQLiteDataBase objesi üzerinden insert methodunu çağırırız.
     *    insert methodu, veri tabanına bir satır eklemek için kullanılır.
     *
     *    insert(table: String, nullColumnHack: String, values: ContentValues!):
     *    Bu method, 3 parametre alır ve geriye bir değer return eder.
     *    Return edilen değer = -1 -> Ekleme işlemi başarısızdır.
     *    Return edilen değer = x -> x = Yeni oluşturulan satırın primary key'i.
     *
     *    table: Veri eklenmek istenen tablonun adı.
     *
     *    nullColumnHack: Ekleme işlemi sırasında null değeri atanamayan bir sütuna geçici
     *    bir sütun adı atayarak null değer atamaya olanak sağlar. (null ver.)
     *
     *    values: Eklenmek istenen veriyi ve hangi sutuna eklenmesi gerektiği bilgisini tutan
     *    ContentValues objesi.
     */

    fun addNote(note: String, date: String) {
        val db: SQLiteDatabase = this.writableDatabase // getWritableDatabase() kodunun kısa hali.
        val cv = ContentValues()
        cv.put("note", note)
        cv.put("date", date)
        val result = db.insert("my_notes_table", null, cv)
        if (result > 0) {
            Toast.makeText(context, "Veri başarılı bir şekilde eklendi.", Toast.LENGTH_SHORT)
                .show()
        } else {
            Toast.makeText(context, "Veri eklenmesi başarısız oldu.", Toast.LENGTH_SHORT)
                .show()
        }
        db.close()
    }

    /**
     * Veritabanından Veri Silme:
     * 1. SQLiteDataBase objesi oluşturulur.
     *    Bu obje, SQLiteOpenHelper class'ındaki getWritableDatabase metotu çağrılacak oluşturulur.
     *    Not: Veri sileceğimiz için getWritableDatabase metodunu çağırıyoruz. Eğer veri okuyacak
     *    olsaydık getReadableDatabase metodunu çağırırdık.
     *
     * 2. Oluşturulan SQLiteDataBase objesi üzerinden delete methodu çağrılır.
     *    Delete methodu, veritabanından bir satır silmek için kullanılır.
     *    Bu method bir değer return eder ve return ettiği değer = silinen satır sayısı.
     *
     *    delete(table: String!, whereClause: String!, )
     *    table: Silinecek verinin bulunduğu tablonun adı.
     *
     *    whereClause: Silinecek satırların sorgusu.
     *    WHERE ifadesinin ardından yazılan koşullarla belirtilir.
     *    Eğer tüm satırlar silinecekse null değeri verilebilir.
     *
     *    whereArgs: whereClause içindeki yer tutucuların değerleri.
     *    whereClause içindeki "?" karakterlerine sırasıyla whereArgs listesindeki değerler atanır.
     */

    fun deleteNote(id: Int) {
        val db = this.writableDatabase
        db.delete("my_notes_table", "id=?", arrayOf(id.toString()))
        db.close()
    }

    /**
     * Veritabanından Veri Okuma
     *
     * 1.SQLiteDataBase objesi oluşturulur.
     *  Bu obje, SQLiteOpenHelper class'ındaki getReadableDatabase metotu çağrılacak oluşturulur.
     *  Not: Veri okuyacağımız için getWritableDatabase metodunu çağırıyoruz.
     *  Eğer veri ekleyecek/silecek olsaydık getWritableDatabase metodunu çağırırdık.
     *
     * 2.Oluşturulan SQLiteDataBase objesi üzerinden query methodu kullanılır.
     *  query methodu, belirtilen SQLite veritabanı tablosundan veri sorgulamak için kullanılır.
     *  Bu method, sorgu sonucu, belirtilen sütunlar ve koşullara uygun satırlardan oluşan bir
     *  Cursor nesnesi return eder.
     *
     * Return edilen Cursor nesnesindeki moveToNext() methodunu kullanarak veritabanındaki
     * satırlar üzerinde gezinebiliriz. (moveToNext() = hasNext() gibi düşün.)
     */

    fun allNotes(): MutableList<Note> {
        val arr = mutableListOf<Note>()
        val db = this.readableDatabase
        val cursor = db.query(
            "my_notes_table", null, null,
            null, null, null, null
        )

        while (cursor.moveToNext()) {
            val id = cursor.getInt(0) // Veritabanındaki 0. sutundaki değeri return eder.
            val note = cursor.getString(1) // Veritabanındaki 1. sutundaki değeri return eder.
            val date = cursor.getString(2) // Veritabanındaki 2. sutundaki değeri return eder.

            val noteObj = Note(id, note, date)
            arr.add(noteObj)
        }

        cursor.close()
        db.close()
        return arr
    }
}