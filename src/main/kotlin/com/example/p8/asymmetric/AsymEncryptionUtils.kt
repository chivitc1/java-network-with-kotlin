package com.example.p8.asymmetric

import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.security.KeyFactory
import java.security.KeyPairGenerator
import java.security.PrivateKey
import java.security.PublicKey
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import java.util.*
import javax.crypto.Cipher
import javax.crypto.Cipher.DECRYPT_MODE
import javax.crypto.Cipher.ENCRYPT_MODE

/**
 * Alg:
RSA
Diffie-Hellman
DSA
 */
const val PRIVATE_KEY_FILE = "p8-private.key"
const val PUBLIC_KEY_FILE = "p8-public.key"
const val KEY_SIZE = 1024

fun main() {
    val keyPairGenerator = KeyPairGenerator.getInstance("RSA")
    keyPairGenerator.initialize(KEY_SIZE)
    val keyPair = keyPairGenerator.genKeyPair()
    val privateKey = keyPair.private
    val publicKey = keyPair.public

    AsymEncryptionUtils.savePrivateKey(privateKey)
    AsymEncryptionUtils.savePublicKey(publicKey)

    val message = "A message"
    println("Message = [$message]")

    val encodedData = AsymEncryptionUtils.encrypt(message, AsymEncryptionUtils.getPublicKey())
    val decryptedMsg = AsymEncryptionUtils.decrypt(encodedData, AsymEncryptionUtils.getPrivateKey())
    println("Decrypted message = [${decryptedMsg}]")
    assert(message == decryptedMsg)
}

class AsymEncryptionUtils {
    companion object {
        fun encrypt(plainTextMessage: String, publicKey: PublicKey): String {
            val cipher = Cipher.getInstance("RSA")
            cipher.init(ENCRYPT_MODE, publicKey)
            val encryptedBytes = cipher.doFinal(plainTextMessage.toByteArray())

            return String(Base64.getEncoder().withoutPadding().encode(encryptedBytes))
        }

        fun decrypt(encryptedMessage: String, privateKey: PrivateKey): String {
            val decodedData = Base64.getDecoder().decode(encryptedMessage.toByteArray())

            val cipher = Cipher.getInstance("RSA")
            cipher.init(DECRYPT_MODE, privateKey)
            val decryptedData = cipher.doFinal(decodedData)

            return String(decryptedData)
        }

        fun savePrivateKey(privateKey: PrivateKey) {
            val pkcs8EncodedKeySpec = PKCS8EncodedKeySpec(privateKey.encoded)
            FileOutputStream(PRIVATE_KEY_FILE).use {
                it.write(pkcs8EncodedKeySpec.encoded)
            }
        }

        fun  savePublicKey(publicKey: PublicKey) {
            val x509EncodedKeySpec = X509EncodedKeySpec(publicKey.encoded)
            FileOutputStream(PUBLIC_KEY_FILE).use {
                it.write(x509EncodedKeySpec.encoded)
            }
        }

        fun getPrivateKey(): PrivateKey {
            val privateKeyFile = File(PRIVATE_KEY_FILE)
            FileInputStream(privateKeyFile).use {
                val encodedPrivateKey = ByteArray(privateKeyFile.length().toInt())
                it.read(encodedPrivateKey)
                val privateKeySpec = PKCS8EncodedKeySpec(encodedPrivateKey)
                val keyFactory = KeyFactory.getInstance("RSA")

                return keyFactory.generatePrivate(privateKeySpec)
            }
        }

        fun getPublicKey(): PublicKey {
            val publicKeyFile = File(PUBLIC_KEY_FILE)
            FileInputStream(publicKeyFile).use {
                val encodedPublicKey = ByteArray(publicKeyFile.length().toInt())
                it.read(encodedPublicKey)
                it.close()

                val publicKeySpec = X509EncodedKeySpec(encodedPublicKey)
                val keyFactory = KeyFactory.getInstance("RSA")

                return keyFactory.generatePublic(publicKeySpec)
            }
        }
    }
}