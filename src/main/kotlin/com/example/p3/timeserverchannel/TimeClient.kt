package com.example.p3.timeserverchannel

import java.net.InetSocketAddress
import java.nio.ByteBuffer
import java.nio.channels.SocketChannel

fun main(args: Array<String>) {
    val SERVER_HOST = "127.0.0.1"
    val SERVER_PORT = 5000
    val BUFFER_SIZE = 64

    val address = InetSocketAddress(SERVER_HOST, SERVER_PORT)

    SocketChannel.open(address).use {
        val buffer = ByteBuffer.allocate(BUFFER_SIZE)
        var bytesRead = it.read(buffer)
        while (bytesRead > 0) {
            buffer.flip()
            while (buffer.hasRemaining()) {
                print(buffer.get().toChar())
            }
            println()
            bytesRead = it.read(buffer)
        }
    }
}