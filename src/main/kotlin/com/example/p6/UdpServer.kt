package com.example.p6

import java.lang.StringBuilder
import java.net.DatagramPacket
import java.net.DatagramSocket

fun main() {
    val PORT = 9003
    println("UDP Server Started at port: $PORT")
    DatagramSocket(PORT).use {
        while (true) {
            val receiveMessages = ByteArray(1024)
            val receivePacket = DatagramPacket(receiveMessages, receiveMessages.size)
            it.receive(receivePacket)
            val receivedSentence = receivePacket.data.getText()
            val remoteAddress = receivePacket.address
            val remotePort = receivePacket.port
            println("Received message : [$receivedSentence]\nFrom : $remoteAddress , at port: $remotePort");
            val sendMessages = receivedSentence.toByteArray()
            val sendPacket = DatagramPacket(sendMessages, sendMessages.size, remoteAddress, remotePort)
            it.send(sendPacket)
        }
    }
    println("UDP Server Terminating")
}

fun ByteArray.getText(): String {
    val sb = StringBuilder()
    this.forEach {
        val ch = it.toChar()
        if (!ch.isISOControl()) {
            sb.append(ch)
        }
    }
    return sb.toString()
}
