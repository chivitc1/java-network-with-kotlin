package com.example.p8.ssl

import com.example.p8.symmetric.KEY_STORE_TYPE
import com.sun.net.ssl.internal.ssl.Provider
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintStream
import java.security.Security
import javax.net.ssl.SSLServerSocketFactory

const val PORT = 5000
const val HOST = "127.0.0.1"
const val SERVER_KEY_STORE_FILE = "p8-serverkeystore.p12"
const val TRUST_KEY_STORE_FILE = "p8-servertruststore.p12"
const val KEY_STORE_PASSWORD = "mypassword"
const val TRUST_KEY_STORE_PASSWORD = "mypassword"
fun main() {
    println("SSL Server Started")

    Security.addProvider(Provider())
    System.setProperty("javax.net.ssl.keyStore", SERVER_KEY_STORE_FILE)
    System.setProperty("javax.net.ssl.keyStorePassword", KEY_STORE_PASSWORD)
    System.setProperty("javax.net.ssl.keyStoreType", KEY_STORE_TYPE)

    System.setProperty("javax.net.ssl.trustStore", TRUST_KEY_STORE_FILE)
    System.setProperty("javax.net.ssl.trustStorePassword", TRUST_KEY_STORE_PASSWORD)
    System.setProperty("javax.net.ssl.trustKeyStoreType", KEY_STORE_TYPE)

    val sslServerSocketFactory = SSLServerSocketFactory.getDefault()
    val serverSocket = sslServerSocketFactory.createServerSocket(PORT)
    serverSocket.use {
        println("Waiting for connection on port: $PORT")
        val socket = it.accept()
        println("Client connected")

        val out = PrintStream(socket.getOutputStream())
        val reader = BufferedReader(InputStreamReader(socket.getInputStream()))

        while (true) {
            val msg = reader.readLine()
            if (msg == null || msg == "quit") {
                break
            }
            println("Client message = [$msg]")
            out.println(msg)
        }
        println("Server terminating")
    }
}