package com.example.p2.multicastdemo

import java.net.DatagramPacket
import java.net.InetAddress
import java.net.MulticastSocket

/**
 * This server only broadcasts messages. It never receives messages from a client.
 */
fun main(args: Array<String>) {
    val PORT = 8888
    val MULTICAST_GROUP = "230.0.0.0"
    println("Multicast client")
    val socket = MulticastSocket(PORT)
    val buffer = ByteArray(256)
    socket.use {
        val addressGroup = InetAddress.getByName(MULTICAST_GROUP)
        it.joinGroup(addressGroup)
        println("Multicast group joined")
        while(true) {
            val packet = DatagramPacket(buffer, buffer.size)
            socket.receive(packet)
            val received = String(packet.data)
            println(received.trim())
        }

//        socket.leaveGroup(addressGroup)
    }
    println("Multicast client terminated")
}