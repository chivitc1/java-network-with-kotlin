package com.example.p6.udpchannel

import java.lang.StringBuilder
import java.net.InetSocketAddress
import java.nio.ByteBuffer
import java.nio.channels.DatagramChannel

const val SERVER_PORT = 9000
const val SERVER_HOST = "127.0.0.1"
fun main() {
    println("Echo server started at port: $SERVER_PORT")

    DatagramChannel.open().use { channel ->
        channel.socket().use {
            val address = InetSocketAddress(SERVER_PORT)
            it.bind(address)
            val buffer = ByteBuffer.allocateDirect(65507)
            while (true) {
                val socket = channel.receive(buffer)
                buffer.flip()

                buffer.mark()
                val sb = StringBuilder()
                while (buffer.hasRemaining()) {
                    sb.append(buffer.get().toChar())
                }
                println("Received message: [${sb}]")
                buffer.reset()

                channel.send(buffer, socket)
                println("Sent message: [${sb}]")
                buffer.clear()
            }
        }
    }

    println("Echo server terminating")
}