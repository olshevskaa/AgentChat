package com.secret.agentchat.data.crypto

import java.security.Key
import java.security.KeyFactory
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.PrivateKey
import java.security.PublicKey
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import java.util.Base64
import javax.crypto.Cipher

object RSAUtils {

    fun generateKeyPair(): KeyPair {
        val keyGen = KeyPairGenerator.getInstance("RSA")
        keyGen.initialize(2048)
        return keyGen.generateKeyPair()
    }

    fun encryptWithPublicKey(data: ByteArray, publicKey: PublicKey): ByteArray {
        val cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding")
        cipher.init(Cipher.ENCRYPT_MODE, publicKey)
        return cipher.doFinal(data)
    }

    fun decryptWithPrivateKey(encryptedData: ByteArray, privateKey: PrivateKey): ByteArray {
        val cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding")
        cipher.init(Cipher.DECRYPT_MODE, privateKey)
        return cipher.doFinal(encryptedData)
    }

    fun publicKeyFromBase64(base64: String): PublicKey {
        val bytes = Base64.getDecoder().decode(base64)
        val keySpec = X509EncodedKeySpec(bytes)
        return KeyFactory.getInstance("RSA").generatePublic(keySpec)
    }

    fun privateKeyFromBase64(base64: String): PrivateKey {
        val bytes = Base64.getDecoder().decode(base64)
        val keySpec = PKCS8EncodedKeySpec(bytes)
        return KeyFactory.getInstance("RSA").generatePrivate(keySpec)
    }

    fun toBase64(key: Key): String = Base64.getEncoder().encodeToString(key.encoded)
}
