package com.example.p4

import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

fun main() {
    sendGet()
}

fun sendGet() {
    val queryUrl = "http://www.google.com/search?q=kotlin&ie=utf-8&oe=utf-8"
    val url = URL(queryUrl)
    val conn = url.openConnection() as HttpURLConnection
    conn.requestMethod = "GET"
    conn.setRequestProperty("User-Agent", "Mozilla/5.0")

    val responseCode = conn.responseCode
    println("Response code: ${responseCode}")

    when (responseCode) {
        200 -> {
            println("response: ${conn.getResponseText()}")
        }
        else -> println("Bad response code: ${responseCode}")
    }

}

private fun HttpURLConnection.getResponseText(): String {
    BufferedReader(InputStreamReader(inputStream)).use {
        return it.readText()
    }
}
