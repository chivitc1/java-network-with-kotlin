package com.example.p7

import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintStream
import java.net.ServerSocket
import java.net.Socket
import java.text.NumberFormat
import java.util.concurrent.ConcurrentHashMap

const val PORT = 5000
const val HOST = "127.0.0.1"
fun main() {
    println("Multi-Threaded Server Started")
    val serverSocket = ServerSocket(PORT)
    while (true) {
        val socket = serverSocket.accept()
        println("Connected to a Client")
        Thread(SimpleMultithreadedServer(socket)).start()
    }
    println("Multi-Threaded Server Terminated")
}

class SimpleMultithreadedServer(val socket: Socket) : Runnable {

    companion object {
        val map = ConcurrentHashMap<String, Float>()
        init {
            map["Axle"] = 238.50f;
            map["Gear"] = 45.55f;
            map["Wheel"] = 86.30f;
            map["Rotor"] = 8.50f;
        }
    }

    override fun run() {
        println("Client Thread Started");

        socket.use {
            val bufferedReader = BufferedReader(InputStreamReader(socket.getInputStream()))
            val ps = PrintStream(socket.getOutputStream())
            val partName = bufferedReader.readLine()
            println("Partname: $partName")
            val price = map.get(partName)
            ps.println(price)
            val numberFormat = NumberFormat.getCurrencyInstance()
            println("Request for [$partName] and return price of [${numberFormat.format(price)}]")
        }
        println("Client Connection Terminated")
        println("Client Thread Terminated");
    }
}