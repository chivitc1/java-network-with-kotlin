package com.example

import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.InetAddress
import java.net.URL
import java.nio.ByteBuffer
import java.nio.channels.Channels

fun inetAddressSample() {
    println("Hello InetAddress")
    val packtAddr = InetAddress.getByName("www.packtpub.com")
    println(packtAddr.hostAddress)
    println(packtAddr.canonicalHostName)
    println(packtAddr.hostName)

    val timeout = 1000
    val result = packtAddr.isReachable(timeout)
    println("Packt is reachable? " + result)
    println("END")
}

fun urlConnSample() {
    val url = URL("http://google.com")
    val conn = url.openConnection()
    val br = BufferedReader(InputStreamReader(conn.getInputStream()))
    br.use {
        it.lineSequence().forEach { println(it) }
    }
}

fun byteBufferSample() {
    val url = URL("http://google.com")

    val conn = url.openConnection()

    val inputStream = conn.getInputStream()
    inputStream.use {
        val channel = Channels.newChannel(it)
        val buffer = ByteBuffer.allocate(64)

        channel.use {
            while (it.read(buffer) > 0) {
                print(String(buffer.array()))
                buffer.clear()
            }
        }
    }
}