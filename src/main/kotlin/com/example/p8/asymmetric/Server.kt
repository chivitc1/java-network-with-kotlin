package com.example.p8.asymmetric

import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintStream
import java.net.ServerSocket

const val HOST = "127.0.0.1"
const val PORT = 5000

fun main() {
    ServerSocket(PORT).use {
        println("Waiting for client connection... on port $PORT")
        val socket = it.accept()
        println("Connected to client")
        val out = PrintStream(socket.getOutputStream())
        val reader = BufferedReader(InputStreamReader(socket.getInputStream()))
        while (true) {
            if (socket.isClosed) {
                println("Terminating due to client disconnect")
                break
            }

            val msg = reader.readLine()
            println("Client encrypted msg = [$msg]")
            val decryptedMsg = AsymEncryptionUtils.decrypt(msg, AsymEncryptionUtils.getPrivateKey())
            println("Client request: [$decryptedMsg]")
            out.println(decryptedMsg)
        }

    }
}