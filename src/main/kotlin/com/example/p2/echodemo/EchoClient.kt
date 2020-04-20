package com.example.p2.echodemo

import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Inet4Address
import java.net.Socket
import java.util.*

fun main(args: Array<String>) {
    val SERVER_PORT = 6000
    println("Echo client")
    println("Waiting for connection")
    val serverAddress = Inet4Address.getLocalHost()

    val clientSocket = Socket(serverAddress, SERVER_PORT)
    val scanner = Scanner(System.`in`)
    clientSocket.use {
        val writer = PrintWriter(clientSocket.getOutputStream(), true)
        val reader = BufferedReader(InputStreamReader(clientSocket.getInputStream()))
        println("Connected to server")

        reader.use {
            while(true) {
                print("Enter text: ")
                val input = scanner.nextLine()
                if ("q" == input) {
                    break;
                }
                writer.println(input)
                val response = it.readLine()
                println("Server response: ${response}" )
            }
        }
    }
}