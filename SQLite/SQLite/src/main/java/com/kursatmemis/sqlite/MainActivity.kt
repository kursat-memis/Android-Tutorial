package com.kursatmemis.sqlite

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.kursatmemis.sqlite.adapters.NoteAdapter
import com.kursatmemis.sqlite.database_helpers.NoteDataBaseHelper
import com.kursatmemis.sqlite.models.Note


/**
 * SQLite Nedir?
 * Android işletim sistemi ile birlikte gelen çok hafif bir veritabanıdır.
 * Verileri bir cihazın local depolama alanında saklamak için SQLite'ı kullanırız.
 * Veriler internet bağlantısına gerek duymadan, kullanıcının cihazında saklanır ve
 * internet bağlantısına ihtiyaç duymadan local olarak saklanmış verilere erişilebilir.
 *
 * Saklanacak olan verilerin boyutları yüksek ise ya da zamanla artan bir veri ise
 * kullanımı önerilmez. (Bu durumda servis kullanmak daha mantıklı olacaktır.)
 */

/**
 * Nasıl Kullanılır?
 *
 * 1. Oluşturulacak veritabanında, oluşturulacak olan tablo için tablonun adını, versiyonunu
 * ve tabloyu oluşturmak için gerekli olan kod tanımını içinde barındıran bir class oluşturulur.
 *
 * 2. SQLiteHelper class'ını extends eden bir class oluşturulur.
 * Bu class içinde yazacağımız methodlar ile veritabanına ekleme, silme, güncelleme ve görüntüleme
 * yapabiliriz.
 * Bu class'ın kullanımı DataBaseHelper class'ı içinde anlatıldı.
 *
 * Not: Her veritabanı işlemi sonrasında close() methodu çağrılarak veritabanı kapatılır.
 */

class MainActivity : AppCompatActivity() {
    private lateinit var addNoteButton: Button
    private lateinit var deleteNoteButton: Button
    private lateinit var showNotesButton: Button
    private lateinit var notesListView: ListView
    private lateinit var noteEditText: EditText
    private lateinit var dateEditText: EditText
    private lateinit var noteIdEditText: EditText
    private lateinit var db: NoteDataBaseHelper
    private lateinit var arr: MutableList<Note>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setViews()

        db = NoteDataBaseHelper(this)

        addNoteButton.setOnClickListener {
            Log.d("calisti", "calisti butn")
            val alertDialogBuilder = AlertDialog.Builder(this@MainActivity)
            val x = layoutInflater.inflate(R.layout.add_note_alert_dialog, null)
            alertDialogBuilder.setView(x)
            alertDialogBuilder.setPositiveButton(
                "Notu Ekle",
                DialogInterface.OnClickListener { dialogInterface, i ->
                    val note = x.findViewById<EditText>(R.id.noteEditText).text.toString()
                    val date = x.findViewById<EditText>(R.id.dateEditText).text.toString()
                    db.addNote(note, date)
                    db.close()
                })
            alertDialogBuilder.show()
        }
        deleteNoteButton.setOnClickListener {
            val alertDialogBuilder = AlertDialog.Builder(this@MainActivity)
            var x = layoutInflater.inflate(R.layout.delete_note_alert_dialog, null)
            alertDialogBuilder.setView(x)
            alertDialogBuilder.setPositiveButton("Notu Sil", DialogInterface.OnClickListener { dialogInterface, i ->
                val noteId = x.findViewById<EditText>(R.id.noteIdEditText).text.toString().toInt()
                db.deleteNote(noteId)
                db.close()
            })
            alertDialogBuilder.show()
        }
        showNotesButton.setOnClickListener {
            val arr = db.allNotes()
            val adapter = NoteAdapter(this, arr)
            notesListView.adapter = adapter
            db.close()
        }

    }

    private fun setViews() {
        addNoteButton = findViewById(R.id.btnEkle)
        deleteNoteButton = findViewById(R.id.btnSil)
        showNotesButton = findViewById(R.id.btnGoruntule)
        notesListView = findViewById(R.id.listView)
        var addNoteAlertDialog = layoutInflater.inflate(R.layout.add_note_alert_dialog, null)
        noteEditText = addNoteAlertDialog.findViewById(R.id.noteEditText)
        dateEditText = addNoteAlertDialog.findViewById(R.id.dateEditText)
        var deleteNoteAlertDialog = layoutInflater.inflate(R.layout.delete_note_alert_dialog, null)
        noteIdEditText = deleteNoteAlertDialog.findViewById(R.id.noteIdEditText)
        noteIdEditText = deleteNoteAlertDialog.findViewById(R.id.noteIdEditText)
    }
}

