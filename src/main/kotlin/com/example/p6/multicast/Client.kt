package com.example.p6.multicast

import java.net.DatagramPacket
import java.net.InetAddress
import java.net.MulticastSocket

fun main() {
    println("UDP Multicast Time Client Started")
    System.setProperty("java.net.preferIPv4Stack", "true");

    MulticastSocket(MULTICAST_PORT).use { mc ->
        val mcAddress = InetAddress.getByName(MULTICAST_ADDRESS)
        mc.joinGroup(mcAddress)

        val bytes = ByteArray(256)
        val packet = DatagramPacket(bytes, bytes.size)
        while (true) {
            mc.receive(packet)
            val message = String(packet.data)
            println("Message from ${packet.address}, message = [${message}]")
        }
    }
    println("UDP Multicast Time Client Terminated")
}