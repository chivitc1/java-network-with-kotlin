package com.example.p8.ssl

import com.example.p8.symmetric.KEY_STORE_TYPE
import com.sun.net.ssl.internal.ssl.Provider
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintStream
import java.security.Security
import java.util.*
import javax.net.ssl.SSLSocketFactory

const val CLIENT_KEY_STORE_FILE = "p8-clientkeystore.p12"
const val CLIENT_TRUST_KEY_STORE_FILE = "p8-clienttruststore.p12"
fun main() {
    println("Client started")

    Security.addProvider(Provider())
    System.setProperty("javax.net.ssl.keyStore", CLIENT_KEY_STORE_FILE)
    System.setProperty("javax.net.ssl.keyStorePassword", KEY_STORE_PASSWORD)
    System.setProperty("javax.net.ssl.keyStoreType", KEY_STORE_TYPE)

    System.setProperty("javax.net.ssl.trustStore", CLIENT_TRUST_KEY_STORE_FILE)
    System.setProperty("javax.net.ssl.trustStorePassword", TRUST_KEY_STORE_PASSWORD)
    System.setProperty("javax.net.ssl.trustKeyStoreType", KEY_STORE_TYPE)

    val sslSocketFactory = SSLSocketFactory.getDefault()
    sslSocketFactory.createSocket(HOST, PORT).use {
        val out = PrintStream(it.getOutputStream())
        val reader = BufferedReader(InputStreamReader(it.getInputStream()))
        val scanner = Scanner(System.`in`)

        while (true) {
            print("Enter message: ")
            val msg = scanner.nextLine()
            if (msg == null || msg == "quit") {
                break
            }
            out.println(msg)
            val response = reader.readLine()
            println("Server response: [$response]")
        }
        println("Client terminating")
    }
}