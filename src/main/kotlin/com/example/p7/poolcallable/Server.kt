package com.example.p7.poolcallable

import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintStream
import java.net.ServerSocket
import java.net.Socket
import java.text.NumberFormat
import java.util.concurrent.Callable
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.Executors

const val PORT = 5000
const val HOST = "127.0.0.1"

fun main() {
    println("Thread Pool Server Started")
    val executor = Executors.newCachedThreadPool()

    ServerSocket(PORT).use {
        while (true) {
            println("Listening for connection...")
            val socket = it.accept()
            println("Client connected")
            val task = WorkerThread(socket)
            println("Task created: $task")
            executor.execute(task)
        }
    }
    executor.shutdown()
    println("Thread Pool Server Terminated")
}

class WorkerThread(val socket: Socket) : Runnable {
    companion object {
        val map = ConcurrentHashMap<String, Float>()
        init {
            map.put("Axle", 11.11f);
            map.put("Gear", 22.22f);
            map.put("Wheel", 33.33f);
            map.put("Rotor", 44.44f);
        }
    }

    override fun run() {
        println("Worker Thread Started")

        socket.use {
            val br = BufferedReader(InputStreamReader(socket.getInputStream()))
            val out = PrintStream(socket.getOutputStream())

            val partName = br.readLine()
            val price = WorkerCallable(partName!!).call()
            out.println(price!!)
            val nf = NumberFormat.getCurrencyInstance()
            println("Request for [$partName] return price = [${nf.format(price)}]")
        }

        println("Worker Thread Terminated")
    }



}

class WorkerCallable(val partName: String) : Callable<Float> {
    override fun call(): Float? {
        val price = WorkerThread.map.get(partName)
        println("WorkerCallabe return price = [$price]")
        return price;
    }
}