package com.example.p2.echodemo

import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.ServerSocket

fun main(args: Array<String>) {
    val SERVER_PORT = 6000
    println("Echo Server")

    val serverSocket = ServerSocket(SERVER_PORT)
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
