package com.example.p3.chat

import java.net.InetSocketAddress
import java.nio.channels.SocketChannel
import java.util.*

fun main(args: Array<String>) {
    val SERVER_HOST = "127.0.0.1"
    val SERVER_PORT = 5000
    val address = InetSocketAddress(SERVER_HOST, SERVER_PORT)
    SocketChannel.open(address).use {
        println("Connected to chat server")
        var message: String
        val scanner = Scanner(System.`in`)
        while (true) {
            println("Waiting for message from server...")
//            println("Message: ${HelperMethods.receiveFixedLengthMessage(it)}")
            println("Message: ${HelperMethods.receiveMessage(it)}")
            print("> ")
            message = scanner.nextLine()
            if ("quit" == message) {
//                HelperMethods.sendFixedLengthMessage(it, "Client terminating")
                HelperMethods.sendMessage(it, "Client terminating")
                break
            }

//            HelperMethods.sendFixedLengthMessage(it, message)
            HelperMethods.sendMessage(it, message)
        }
    }
}