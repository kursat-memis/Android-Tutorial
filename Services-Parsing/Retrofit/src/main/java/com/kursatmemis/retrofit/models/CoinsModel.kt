package com.kursatmemis.retrofit.models

data class CoinsModel (
    val status: String,
    val data: Data
)

data class Data (
    val stats: Stats,
    val coins: List<Coin>
)

data class Coin (
    val uuid: String? = null,
    val symbol: String? = null,
    val name: String? = null,
    val color: String? = null,
    val iconURL: String? = null,
    val marketCap: String? = null,
    val price: String? = null,
    val listedAt: Long? = null,
    val tier: Long? = null,
    val change: String? = null,
    val rank: Long? = null,
    val sparkline: List<String>? = null,
    val lowVolume: Boolean? = null,
    val coinrankingURL: String? = null,
    val the24HVolume: String? = null,
    val btcPrice: String? = null
)

data class Stats (
    val total: Long,
    val totalCoins: Long,
    val totalMarkets: Long,
    val totalExchanges: Long,
    val totalMarketCap: String,
    val total24HVolume: String
)
