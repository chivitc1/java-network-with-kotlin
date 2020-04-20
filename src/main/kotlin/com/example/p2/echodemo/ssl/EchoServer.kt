package com.example.p2.echodemo.ssl

import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import javax.net.ssl.SSLServerSocketFactory

fun main(args: Array<String>) {
    val SERVER_PORT = 6000
    System.setProperty("javax.net.ssl.keyStore", "exampleKeyStore.jks");
    System.setProperty("javax.net.ssl.keyStorePassword", "123456");
    println("Echo Server")

    val ssf = SSLServerSocketFactory.getDefault()
    val serverSocket = ssf.createServerSocket(SERVER_PORT)
    serverSocket.use {
        println("Waiting for conn...")
        val clientSocket = it.accept()
        println("Connected to client")

        val br = BufferedReader(InputStreamReader(clientSocket.getInputStream()))
        val writer = PrintWriter(clientSocket.getOutputStream(), true)


        var line : String?
        br.use {
            while (true) {
                line = it.readLine()
                if (line != null) {
                    println("Ping from client: " + line)
                    writer.println("Echo ${line}")
                } else {
                    break
                }
            }
        }
    }
    println("Echo server terminating...")
}
