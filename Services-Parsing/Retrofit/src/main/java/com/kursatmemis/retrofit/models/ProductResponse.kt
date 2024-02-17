package com.kursatmemis.retrofit.models

import com.google.gson.annotations.SerializedName

/**
 * Server'dan dönecek yanıt ile Data Class'ın parametlerindeki variable isimlerinin aynı
 * olması gerekiyor. Ancak eğer data class'daki varaible isminin sunucudakinden farklı olmasını
 * istiyorsak bunu @SerializedName annotation'ı ile sağlarız. Bu annotation içine sunucudaki
 * değişkenin adını aynen yazarız ve data class'daki değişken adını artık istediğimiz gibi
 * belirleyebiliriz.
 */

data class ProductResponse (
    val products: List<Product>,
    val total: Long,
    val skip: Long,
    @SerializedName("limit")
    val bu_degisken_sunucudaki_limiti_temsil_ediyor: Long
)

data class Product (
    val id: Long,
    val title: String,
    val description: String,
    val price: Long,
    val discountPercentage: Double,
    val rating: Double,
    val stock: Long,
    val brand: String,
    val category: String,
    val thumbnail: String,
    val images: List<String>
)
