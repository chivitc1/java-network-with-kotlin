package com.example.p4

import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStream
import java.lang.StringBuilder
import java.net.InetAddress
import java.net.Socket

fun main(args: Array<String>) {
    val SERVER_HOST = "127.0.0.1"
    val SERVER_PORT = 3000
    println("HTTP client started")
    val address = InetAddress.getByName(SERVER_HOST)

    Socket(address, SERVER_PORT).use {
        sendGetRequest(it.getOutputStream())
        println(getResponseText(BufferedReader(InputStreamReader(it.getInputStream()))))
    }
}

fun getResponseText(br: BufferedReader): String {
    val sb = StringBuilder()
    br.forEachLine {
        sb.append(it).append("\n")
    }
    return sb.toString()
}

fun sendGetRequest(out: OutputStream) {
    out.write("GET /default${NEW_LINE}".toByteArray())
    out.write("User-Agent: Mozilla/5.0${NEW_LINE}".toByteArray())
}
