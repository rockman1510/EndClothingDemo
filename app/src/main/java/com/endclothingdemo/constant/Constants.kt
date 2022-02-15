package com.endclothingdemo.constant

class Constants {
    companion object {
        const val CACHE_SIZE = (10 * 1024 * 1024).toLong()
        const val READ_TIMEOUT = 30L
        const val WRITE_TIMEOUT = 45L
        const val CONNECT_TIMEOUT = 30L
        const val CACHE_CONTROL = "Cache-Control"
        const val TIME_CACHE_ONLINE = "public, max-age = 60" // 1 minute
        const val TIME_CACHE_OFFLINE = "public, only-if-cached, max-stale = 86400"
        const val LIMIT_LOADING_ITEM_NUMBER = 25
        const val LOADING_DIALOG_TIMEOUT = 15000L
    }
}