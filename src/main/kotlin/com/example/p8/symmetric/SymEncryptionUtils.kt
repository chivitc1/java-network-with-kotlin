package com.example.p8.symmetric

import java.util.*
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

class SymEncryptionUtils {
    companion object {
        fun encrypt(plainText: String, secretKey: SecretKey): String {
            val cipher = Cipher.getInstance("AES")
            cipher.init(Cipher.ENCRYPT_MODE, secretKey)
            val plainTextBytes = plainText.toByteArray()
            val encryptedBytes = cipher.doFinal(plainTextBytes)

            val encoder = Base64.getEncoder()
            return encoder.encodeToString(encryptedBytes)
        }

        fun decrypt(encryptedText: String, secretKey: SecretKey): String {
            val decoder = Base64.getDecoder()
            val encryptedBytes = decoder.decode(encryptedText)

            val cipher = Cipher.getInstance("AES")
            cipher.init(Cipher.DECRYPT_MODE, secretKey)
            val decryptedBytes = cipher.doFinal(encryptedBytes)

            return String(decryptedBytes)
        }

    }
}

fun generateSecretKey() : SecretKey {
    val keyGenerator = KeyGenerator.getInstance("AES")
    return keyGenerator.generateKey()
}

/**
 * Symmetric Alg:
AES (128)
DES (56)
DESede (168)
HmacSHA1
HmacSHA256
 */
fun main() {
    val message = "Hello world"
    println("Original msg = [$message]")
    val secretKey = generateSecretKey()
    val encryptedMsg = SymEncryptionUtils.encrypt(message, secretKey)
    println("Encrypted msg = [$encryptedMsg]")
    val decryptedMsg = SymEncryptionUtils.decrypt(encryptedMsg, secretKey)
    println("Decrypted msg = [$decryptedMsg]")


    val encodedMsg = Base64.getEncoder().encodeToString(message.toByteArray())
    println("Encoded: $encodedMsg")
    val d = Base64.getDecoder().decode(encodedMsg)
    val decodedMsg = String(d)
    println("Decoded: [$decodedMsg]")
}