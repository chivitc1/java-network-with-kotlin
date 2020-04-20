package com.example.p2.multicastdemo

import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import java.util.*

/**
 * This server only broadcasts messages. It never receives messages from a client.
 */

fun main(args: Array<String>) {
    val PORT = 8888
    val MULTICAST_GROUP = "230.0.0.0"
    println("Multicast server")
    System.setProperty("java.net.preferIPv4Stack", "true")
    val serverSocket: DatagramSocket = DatagramSocket()
    serverSocket.use {
        while(true) {
            val dateText = Date().toString()
            var buffer = dateText.toByteArray()
            val addressGroup = InetAddress.getByName(MULTICAST_GROUP)
            val packet = DatagramPacket(buffer, buffer.size, addressGroup, PORT)
            it.send(packet)
            println("Time sent: $dateText")
            Thread.sleep(2000)
        }
    }
}