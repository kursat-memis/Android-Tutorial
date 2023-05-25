package com.kursatmemis.xml_parsing

data class Currency(
    val unit: String,
    val isim: String,
    val currencyName: String,
    val forexBuying: String,
    val forexSelling: String,
    val bankNoteBuying: String,
    val bankNoteSelling: String,
    val crossRateUSD: String,
    val crossRateOther: String
)
