package com.example.p8.symmetric

import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.security.KeyStore
import javax.crypto.KeyGenerator

/**
 * Generate AES sym secret key and store in keystore file with keystore password protection
 */

const val KEY_STORE_FILE = "p8-secretKeyStore.p12"

/**
 * key store types: JCEKS, JKS, PKCS12
 */
fun main() {
    val keyStore = createKeyStore(KEY_STORE_FILE, KEY_STORE_PASSWORD)
    val keyGenerator = KeyGenerator.getInstance("AES")
    val secretKey = keyGenerator.generateKey()

    val keyStoreEntry = KeyStore.SecretKeyEntry(secretKey)
    val keyPassword = KeyStore.PasswordProtection(KEY_PASSWORD.toCharArray())
    keyStore.setEntry("secretKey", keyStoreEntry, keyPassword)
    keyStore.store(FileOutputStream(KEY_STORE_FILE), KEY_STORE_PASSWORD.toCharArray())
}

fun createKeyStore(fileName: String, keyStorePassword: String): KeyStore {
    val keyStore = KeyStore.getInstance(KEY_STORE_TYPE)
    val file = File(fileName)
    if (file.exists()) {
        keyStore.load(FileInputStream(file), keyStorePassword.toCharArray())
    } else {
        keyStore.load(null, null)
        keyStore.store(FileOutputStream(file), keyStorePassword.toCharArray())
    }
    return keyStore
}