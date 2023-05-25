package com.example.customarrayadapter.datas

class ItemData {

    companion object{
        fun createData(): List<String> {
            val data = mutableListOf<String>()
            for (i in 1..30) {
                val item = "Item - $i"
                data.add(item)
            }
            return data
        }
    }

}