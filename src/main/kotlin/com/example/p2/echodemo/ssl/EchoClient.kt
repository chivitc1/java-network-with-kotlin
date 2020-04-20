package com.example.p2.echodemo.ssl

import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Inet4Address
import java.util.*
import javax.net.ssl.SSLSocketFactory

fun main(args: Array<String>) {
    System.setProperty("javax.net.ssl.trustStore", "/Users/chinv/dev/learns/java-learns/networking-kotlin/exampleKeyStore.jks");
    System.setProperty("javax.net.ssl.trustStorePassword", "123456");

    val SERVER_PORT = 6000
    println("Echo client")
    println("Waiting for connection")
    val serverAddress = Inet4Address.getLocalHost()
    val ssf = SSLSocketFactory.getDefault()

    val clientSocket = ssf.createSocket(serverAddress, SERVER_PORT)
    val scanner = Scanner(System.`in`)
    clientSocket.use {
        val writer = PrintWriter(clientSocket.getOutputStream(), true)
        val reader = BufferedReader(InputStreamReader(clientSocket.getInputStream()))
        println("Connected to server")

        reader.use {
            while(true) {
                print("Enter text: ")
                val input = scanner.nextLine()
                if ("q" == input) {
                    break;
                }
                writer.println(input)
                val response = it.readLine()
                println("Server response: ${response}" )
            }
        }
    }
}