package com.example.p3.asyn

import com.example.p3.chat.HelperMethods
import java.net.InetSocketAddress
import java.nio.ByteBuffer
import java.nio.channels.AsynchronousServerSocketChannel

const val HOST_NAME = "localhost"
const val PORT = 5000

fun main(args: Array<String>) {
    println("Server started")
    val address = InetSocketAddress(HOST_NAME, PORT)
    AsynchronousServerSocketChannel.open().use {
        it.bind(address)
        println("Waiting for client to connect at port: $PORT ...")
        val future = it.accept()
        val channel = future.get()
        channel.use {
            println("Message from client:")
            while (channel.isOpen) {
                val buffer = ByteBuffer.allocate(32)
                val futureResult = channel.read(buffer)

//                futureResult.get(10, TimeUnit.SECONDS)
                while (!futureResult.isDone) {
                    //nothing
                }
                buffer.flip()

                val message = HelperMethods.getString(buffer)
                println(message)
                if ("quit" == message) {
                    break
                }
            }
        }
    }
    println("Terminating")
}