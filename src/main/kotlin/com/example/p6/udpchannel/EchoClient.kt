package com.example.p6.udpchannel

import java.net.InetSocketAddress
import java.nio.ByteBuffer
import java.nio.channels.DatagramChannel

fun main() {
    println("UDP echo client started")
    val serverAddress = InetSocketAddress(SERVER_HOST, SERVER_PORT)
    val channel = DatagramChannel.open()
    channel.connect(serverAddress).use {
        val message = "A message"
        val buffer = ByteBuffer.allocate(message.length)
        buffer.put(message.toByteArray())
        buffer.flip()
        it.write(buffer)
        println("Sent : [ " + message + " ]");
        buffer.clear()
        it.read(buffer)
        buffer.flip()
        print("Received : [")
        while (buffer.hasRemaining()) {
            print(buffer.get().toChar())
        }
        println(" ]")

    }

    println("UDP echo client terminating")
}