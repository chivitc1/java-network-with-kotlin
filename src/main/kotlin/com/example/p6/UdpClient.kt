package com.example.p6

import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import java.util.*

fun main() {
    val SERVER_ADDRESS = InetAddress.getByName("127.0.0.1")
    val SERVER_PORT = 9003
    println("UDP client started")
    val scanner = Scanner(System.`in`)

    DatagramSocket().use {
        while (true) {
            print("Enter a message: ")
            val message: String = scanner . nextLine ()
            if (message == "quit") {
                break
            }
            val sendMessage = message.toByteArray()
            val sendPacket = DatagramPacket(sendMessage, sendMessage.size, SERVER_ADDRESS, SERVER_PORT)
            it.send(sendPacket)

            val receiveMessages = ByteArray(1024)
            val receivePacket = DatagramPacket(receiveMessages, receiveMessages.size)
            it.receive(receivePacket)
            val receivedSentence = receivePacket.data.getText()
            println("Received message [$receivedSentence]\nfrom ${receivePacket.address}")
        }
    }
    println("UDP client terminating")
}