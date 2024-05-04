package com.kursatmemis.recyclerview.factory

import com.kursatmemis.recyclerview.model.Student

class StudentListFactory {

    companion object {
        fun createStudentList() : ArrayList<Student> {
            val dataSource = ArrayList<Student>()

            for (number in 1..100) {
                val name = "Item - $number"
                val age = number
                val student = Student(name, age)
                dataSource.add(student)
            }

            return dataSource
        }
    }

}