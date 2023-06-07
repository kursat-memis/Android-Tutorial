package com.kursatmemis.room.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @Entity: Oluşturulan DataClass'ın, veritabanında bir tabloya karşılık geldiğini belirtir.
 *         'tableName' parametresi ile, veritabanında oluşacak olan tablonun adını belirtiriz.
 *
 * @ColumnInfo: Veritabanındaki sutunların oluşturulmasını sağlar.
 *              'name' parametresi ile sutun adlarını belirleyebiliriz.
 *
 *              Not: Aşağıdaki örnekte, veritabanında oluşacak olan sutun isimleri;
 *              'id, full_name ve class' dır. Ancak biz bu sutunları programatik düzeyde;
 *              'studentId, studentFullName ve studentClass' attribute'leri ile kontrol ederiz.
 *
 * @PrimaryKey: Belirtilen sutunun veritabanında primary key olmasını sağlar.
 *              'autoGenerate' parametresi ile, bu sutunun veri eklendikçe
 *              otomatik olarak artmasını sağlarız.
 */


@Entity(tableName = "student_table")
data class Student(
    @PrimaryKey(autoGenerate = true)
    // @ColumnInfo(name = "studentId") Eğer veritabanındaki sutun adı ve programdaki attribute adı aynı ise bunu eklemene gerek yok.
    val studentId: Int?,
    @ColumnInfo(name = "full_name")
    val studentFullName: String?,
    @ColumnInfo(name = "class")
    val studentClass: String?
)
