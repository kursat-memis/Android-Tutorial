package com.kursatmemis.delegation

fun main() {
    val myClass = MyClass()
    myClass.myClassSum()
}

interface Base{
    fun sum(): Int
}

class BaseImplementation(private val num1: Int, private val num2: Int) : Base {
    override fun sum(): Int {
        return num1 + num2
    }
}

class MyClass: X(), Base by BaseImplementation(10, 20) {
    fun myClassSum() {
        println("Sum: ${sum() + 100}")
    }
}

open class X