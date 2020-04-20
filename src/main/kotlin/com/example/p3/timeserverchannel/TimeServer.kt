package com.example.p3.timeserverchannel

import java.net.InetSocketAddress
import java.nio.ByteBuffer
import java.nio.channels.ServerSocketChannel
import java.time.LocalDateTime

fun main(args: Array<String>) {
    val PORT = 5000
    val BUFFER_SIZE = 64
    println("Timer server started on port ${PORT}")
    ServerSocketChannel.open().use {
        it.socket().bind(InetSocketAddress(PORT))
        while (true) {
            println("Waiting for request")
            val socketChannel = it.accept()
            if (socketChannel != null) {
                val message = "Date: ${LocalDateTime.now()}"
                val buff = ByteBuffer.allocate(BUFFER_SIZE)
                buff.put(message.toByteArray())
                buff.flip()
                while (buff.hasRemaining()) {
                    socketChannel.write(buff)
                }
                println("Sent: ${message}")
            }
        }
    }
}