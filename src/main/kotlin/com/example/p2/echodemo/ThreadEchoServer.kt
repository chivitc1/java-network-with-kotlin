package com.example.p2.echodemo

import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.ServerSocket

fun main(args: Array<String>) {
    val SERVER_PORT = 6000
    println("Threaded Echo Server")

    val serverSocket = ServerSocket(SERVER_PORT)
    serverSocket.use {

        while(true) {
            println("Waiting for conn...")
            val clientSocket = it.accept()
            println("Connected to client")

            val t = Runnable {
                val br = BufferedReader(InputStreamReader(clientSocket.getInputStream()))
                val writer = PrintWriter(clientSocket.getOutputStream(), true)
                br.use {
                    while (true) {
                        val line = it.readLine()
                        if (line != null) {
                            println("Ping from client: " + line)
                            writer.println("Echo ${line}")
                        } else {
                            break
                        }
                    }
                }
            }
            Thread(t).start()
        }



    }
    println("Echo server terminating...")
}
