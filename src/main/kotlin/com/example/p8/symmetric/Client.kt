package com.example.p8.symmetric

import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintStream
import java.net.Socket
import java.util.*

fun main() {
    println("Simple Echo Client")

    Socket(HOST, PORT).use {
        val out = PrintStream(it.getOutputStream())
        val reader = BufferedReader(InputStreamReader(it.getInputStream()))
        println("Connected to server")
        val scanner = Scanner(System.`in`)
        while (true) {
            print("Enter text : ")
            val line = scanner.nextLine()
            if ("quit" == line) {
                break
            }

            val encryptedMsg = SymEncryptionUtils.encrypt(line, KeyStoreUtil.getSecretKey())
            out.println(encryptedMsg)

            val response = reader.readLine()
            println("Server response: [$response]")
        }
    }
}