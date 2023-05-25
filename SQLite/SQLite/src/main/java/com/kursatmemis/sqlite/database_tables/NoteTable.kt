package com.kursatmemis.sqlite.database_tables

/**
 * Veritabanının adı, versiyonu ve veritabanını oluşturmak için kullanılacak kod static olarak
 * tanımlandı.
 */
class NoteTable {
    companion object {
        val DATABASE_NAME = "my_notes"
        var VERSION = 1

        val TABLE_NOTE_CREATE = "CREATE TABLE \"my_notes_table\" (\n" +
                "\t\"id\"\tINTEGER NOT NULL UNIQUE,\n" +
                "\t\"note\"\tTEXT,\n" +
                "\t\"date\"\tTEXT NOT NULL,\n" +
                "\tPRIMARY KEY(\"id\" AUTOINCREMENT)\n" +
                ");"

    }
}