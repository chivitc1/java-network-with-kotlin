package com.example.p6.multicast

import java.net.DatagramPacket
import java.net.InetAddress
import java.net.MulticastSocket
import java.time.LocalDateTime

const val MULTICAST_ADDRESS = "224.0.0.0"
const val MULTICAST_PORT = 9877
fun main() {
    println("UDP Multicast Time Server Started")
    System.setProperty("java.net.preferIPv4Stack", "true");
    MulticastSocket().use { mc ->
        val mcAddress = InetAddress.getByName(MULTICAST_ADDRESS)
        mc.joinGroup(mcAddress)
        var bytes: ByteArray
        var packet: DatagramPacket
        while (true) {
            Thread.sleep(1000)
            val message = LocalDateTime.now().toString()
            println("Sending message = [$message]")
            bytes = message.toByteArray()
            packet = DatagramPacket(bytes, message.length, mcAddress, MULTICAST_PORT)
            mc.send(packet)
        }
        println("UDP Multicast Time Server Terminated")
    }

}