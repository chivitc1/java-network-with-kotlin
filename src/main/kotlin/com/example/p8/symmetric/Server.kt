package com.example.p8.symmetric

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader
import java.io.PrintStream
import java.net.ServerSocket
import java.security.KeyStore
import javax.crypto.SecretKey

const val HOST = "127.0.0.1"
const val PORT = 5000
const val KEY_STORE_PASSWORD = "mykeystorepassword"
const val KEY_PASSWORD = "mykeypassword"

fun main() {
    println("Simple Echo Server")
    ServerSocket(PORT).use {
        println("Waiting for connection...")
        val socket = it.accept()
        println("Connected to client")
        val br = BufferedReader(InputStreamReader(socket.getInputStream()))
        val out = PrintStream(socket.getOutputStream())
        do {
            val line = br.readLine()
            if (line != null) {
                val decryptedText = SymEncryptionUtils.decrypt(line, KeyStoreUtil.getSecretKey())
                println("Client request: [$decryptedText]")
                out.println(decryptedText)
            }
        } while (line != null)
    }
}

