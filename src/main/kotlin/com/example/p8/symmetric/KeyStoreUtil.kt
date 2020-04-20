package com.example.p8.symmetric

import java.io.FileInputStream
import java.security.KeyStore
import javax.crypto.SecretKey

/**
 * Java8 support keystore types: JKS, JCEKS,vPKCS12, PKCS11, and DKS
 */
const val KEY_STORE_TYPE = "PKCS12"
class KeyStoreUtil {
    companion object {
        fun getSecretKey(): SecretKey {
            val keyStore = KeyStore.getInstance(KEY_STORE_TYPE)
            keyStore.load(FileInputStream(KEY_STORE_FILE), KEY_STORE_PASSWORD.toCharArray())

            val keyPassword = KeyStore.PasswordProtection(KEY_PASSWORD.toCharArray())
            val entry = keyStore.getEntry("secretKey", keyPassword)

            return (entry as KeyStore.SecretKeyEntry).secretKey
        }
    }
}