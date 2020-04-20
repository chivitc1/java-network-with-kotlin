package com.example.p3.chat

import java.net.InetSocketAddress
import java.nio.channels.ServerSocketChannel
import java.util.*

fun main(args: Array<String>) {
    val SERVER_PORT = 5000
    ServerSocketChannel.open().use {
        it.socket().bind(InetSocketAddress(5000))
        println("Server chat started on port: ${SERVER_PORT}")
        var running = true
        val scanner = Scanner(System.`in`)
        while (running) {
            println("Waiting for request")
            val socketChannel = it.accept()
            println("Connected to client")
            var message: String?
            while (true) {
                print("> ")
                message = scanner.nextLine()
                if ("quit".equals(message)) {
//                    HelperMethods.sendFixedLengthMessage(socketChannel, "Server terminating")
                    HelperMethods.sendMessage(socketChannel, "Server terminating")
                    running = false
                    break
                }

//                HelperMethods.sendFixedLengthMessage(socketChannel, message)
                HelperMethods.sendMessage(socketChannel, message)
                println("Waiting for message from client...")
//                println("Message: ${HelperMethods.receiveFixedLengthMessage(socketChannel)}")
                println("Message: ${HelperMethods.receiveMessage(socketChannel)}")
            }

        }
    }
}