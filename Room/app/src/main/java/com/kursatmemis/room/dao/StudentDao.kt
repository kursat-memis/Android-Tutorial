package com.kursatmemis.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.kursatmemis.room.models.Student

/**
 * @Dao: Bu annotation sayesinde Room kütüphanesi, bu interface'in veritabanı işlemlerinin (ekleme-silme-güncelleme)
 * tanımlanması için kullanılacağını bilir.
 *
 * @Insert: Veritabanına veri eklenmesini sağlar.
 * @Delete: Veritabanından veri silinmesini sağlar.
 * @Query: İçindeki SQL sorgusunun veritabanı üzerinde çalışmasını sağlar.
 * @Update: Veritabanındaki bir satırın güncellenmesini sağlar.
 */

@Dao
interface StudentDao {

    @Insert
    fun insert(student: Student): Long // Eklenen öğenin primary key'ini return eder.

    @Insert
    fun insertMultiStudents(students: List<Student>): List<Long> // Eklenen öğelerin primary key'lerini return eder.

    @Delete
    fun deleteStudent(student: Student): Int // Etkilenen satır sayısını return eder.

    @Delete
    fun deleteTwoStudents(students: List<Student>): Int // Etkilenen satır sayısını return eder.

    @Query("Delete from student_table")
    fun deleteAllStudents()

    @Update()
    fun update(student: Student): Int // Etkilenen satır sayısını return eder.

    @Query("Select * from student_table")
    fun getAllStudents(): List<Student>
}