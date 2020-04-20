package com.example.p8.asymmetric

import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintStream
import java.net.Socket
import java.util.*

fun main() {
    Socket(HOST, PORT).use {
        val out = PrintStream(it.getOutputStream())
        val reader = BufferedReader(InputStreamReader(it.getInputStream()))

        val scanner = Scanner(System.`in`)
        while (true) {
            print("Enter text: ")
            val line = scanner.nextLine()
            if (line == "quit" || line == null) {
                break
            }

            assert(AsymEncryptionUtils.getPublicKey() != null)
            val encryptedMsg = AsymEncryptionUtils.encrypt(line, AsymEncryptionUtils.getPublicKey())

            out.println(encryptedMsg)
            val response = reader.readLine()
            println("Server response: [$response]")
        }
    }
}