package com.example.p7

import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintStream
import java.net.Socket

fun main() {
    println("Client Started")
    Socket(HOST, PORT).use {
        println("Connected to a Server")
        it.soTimeout = 3000
        val ps = PrintStream(it.getOutputStream())
        val inputStreamReader = InputStreamReader(it.getInputStream())
        val bufferedReader = BufferedReader(inputStreamReader)

        var partName = "Axle"
        println("Send partName = [$partName]")
        ps.println(partName)
        println("Response: [${bufferedReader.readLine()}]")
    }

    Socket(HOST, PORT).use {
        println("Connected to a Server")
        it.soTimeout = 3000
        val ps = PrintStream(it.getOutputStream())
        val inputStreamReader = InputStreamReader(it.getInputStream())
        val bufferedReader = BufferedReader(inputStreamReader)

        val partName = "Wheel"
        println("Send partName = [$partName]")
        ps.println(partName)
        println("Response: [${bufferedReader.readLine()}]")
    }
}