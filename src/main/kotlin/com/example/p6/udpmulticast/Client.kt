package com.example.p6.udpmulticast

import java.lang.StringBuilder
import java.net.InetAddress
import java.net.InetSocketAddress
import java.net.NetworkInterface
import java.net.StandardSocketOptions
import java.nio.ByteBuffer
import java.nio.channels.DatagramChannel

fun main() {
    System.setProperty("java.net.preferIPv6Stack", "true");
    val nic = NetworkInterface.getByName("en0")
    val channel = DatagramChannel.open().bind(InetSocketAddress(PORT))
            .setOption(StandardSocketOptions.IP_MULTICAST_IF, nic)
    val multicastAddress = InetAddress.getByName("FF01:0:0:0:0:0:0:FC")
    val membershipKey = channel.join(multicastAddress, nic)
    println("Joined Multicast Group : $membershipKey")
    println("Waiting for a message...")
    val buffer = ByteBuffer.allocate(1024)
    channel.receive(buffer)
    buffer.flip()
    print("Receive: [")
    val sb = StringBuilder()
    while (buffer.hasRemaining()) {
        sb.append(buffer.get().toChar())
    }
    println("$sb]")
    membershipKey.drop()
}