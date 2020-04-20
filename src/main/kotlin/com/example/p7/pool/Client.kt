package com.example.p7.pool

import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintStream
import java.net.Socket

fun main() {
    println("Client Started")

    Socket(HOST, PORT).use {
        println("Connected to server: ${it.remoteSocketAddress}")
        val out = PrintStream(it.getOutputStream())
        val reader = BufferedReader(InputStreamReader(it.getInputStream()))

        val partName = "Wheel"
        out.println(partName)

        println("Sent request partName = [$partName]")
        println("Response price = [${reader.readLine()}]")
    }

    println("Client Terminated")
}