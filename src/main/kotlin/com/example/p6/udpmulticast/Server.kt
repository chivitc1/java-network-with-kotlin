package com.example.p6.udpmulticast

import java.lang.StringBuilder
import java.net.InetSocketAddress
import java.net.NetworkInterface
import java.net.StandardSocketOptions
import java.nio.ByteBuffer
import java.nio.channels.DatagramChannel

const val PORT = 9003
fun main() {
    val networkInterfaceEnumeration = NetworkInterface.getNetworkInterfaces()
    for (nic in networkInterfaceEnumeration) {
        println(nic.displayName)
    }
    System.setProperty("java.net.preferIPv6Stack", "true")
    val datagramChannel = DatagramChannel.open()
    val nic = NetworkInterface.getByName("en0")
    datagramChannel.setOption(StandardSocketOptions.IP_MULTICAST_IF, nic)
    val multicastAddress = InetSocketAddress("FF01:0:0:0:0:0:0:FC", PORT)
    val message = "The message"
    val buffer = ByteBuffer.allocate(message.length)
    buffer.put(message.toByteArray())
    while (true) {
        datagramChannel.send(buffer, multicastAddress)
        println("Sent the multicast message [$message]");
        buffer.clear()
        buffer.mark()

        print("Sent: [")
        val sb = StringBuilder()
        while (buffer.hasRemaining()) {
            sb.append(buffer.get().toChar())
        }
        println("$sb]")
        buffer.reset()
        Thread.sleep(1000)

    }
}