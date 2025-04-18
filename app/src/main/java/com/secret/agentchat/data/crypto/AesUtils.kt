package com.secret.agentchat.data.crypto

import java.security.SecureRandom
import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object AESUtils {

    fun generateAESKey(): SecretKey {
        val keyGen = KeyGenerator.getInstance("AES")
        keyGen.init(256)
        return keyGen.generateKey()
    }

    fun encrypt(data: String, key: SecretKey): Pair<String, String> {
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        val iv = ByteArray(16).also { SecureRandom().nextBytes(it) }
        cipher.init(Cipher.ENCRYPT_MODE, key, IvParameterSpec(iv))
        val encrypted = cipher.doFinal(data.toByteArray(Charsets.UTF_8))
        return Base64.getEncoder().encodeToString(encrypted) to Base64.getEncoder().encodeToString(iv)
    }

    fun decrypt(encryptedData: String, iv: String, key: SecretKey): String {
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        val ivSpec = IvParameterSpec(Base64.getDecoder().decode(iv))
        cipher.init(Cipher.DECRYPT_MODE, key, ivSpec)
        val decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedData))
        return String(decryptedBytes, Charsets.UTF_8)
    }

    fun keyFromBase64(base64: String): SecretKey {
        val bytes = Base64.getDecoder().decode(base64)
        return SecretKeySpec(bytes, 0, bytes.size, "AES")
    }

    fun toBase64(key: SecretKey): String = Base64.getEncoder().encodeToString(key.encoded)
}
