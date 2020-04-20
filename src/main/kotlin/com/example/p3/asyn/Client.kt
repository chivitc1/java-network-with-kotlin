package com.example.p3.asyn

import java.net.InetSocketAddress
import java.nio.ByteBuffer
import java.nio.channels.AsynchronousSocketChannel
import java.util.*

fun main(args: Array<String>) {
    println("Async client started")
    val address = InetSocketAddress(HOST_NAME, PORT)
    AsynchronousSocketChannel.open().use {
        val future = it.connect(address)
        future.get()
        println("Connected: ${it.isOpen}")
        println("Sending messages to server : ")
        val scanner = Scanner(System.`in`)
        var message: String
        while (true) {
            print("> ")
            message = scanner.nextLine()
            val buffer = ByteBuffer.wrap(message.toByteArray())
            val futureResult = it.write(buffer)
            while(!futureResult.isDone) {
                //nothing
            }
            if (message == "quit") {
                break
            }
        }

    }
}