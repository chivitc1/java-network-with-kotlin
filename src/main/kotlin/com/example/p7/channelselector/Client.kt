package com.example.p7.channelselector

import java.lang.StringBuilder
import java.net.InetSocketAddress
import java.nio.ByteBuffer
import java.nio.channels.SocketChannel
import java.util.*

fun main() {
    val random = Random()
    val address = InetSocketAddress(HOST, PORT)

    SocketChannel.open(address).use {
        while (true) {
            val buffer = ByteBuffer.allocate(64)
            var bytesRead = it.read(buffer)
            while (bytesRead > 0) {
                buffer.flip()
                val sb = StringBuilder()
                while (buffer.hasRemaining()) {
                    sb.append(buffer.get().toChar())
                }
                println(sb)
                buffer.clear()
                bytesRead = it.read(buffer)
            }
            Thread.sleep(random.nextLong() + 1000)
        }
    }
}