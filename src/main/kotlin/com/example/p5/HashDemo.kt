package com.example.p5

import java.security.MessageDigest

fun main() {
    val input = "String to be hashed"
    val hash = input.getHash()
    println(hash)
}

fun String.getHash(): String {
    val messageDigest = MessageDigest.getInstance("SHA-1")
    messageDigest.update(this.toByteArray())
    val bytes = messageDigest.digest()
    return bytes.toHexString()
}

private fun ByteArray.toHexString(): String {
    return this.joinToString("") {
        String.format("%02x", it)
    }
}
