package com.secret.agentchat.data.keystore

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import java.security.KeyPairGenerator
import java.security.KeyStore
import java.security.PrivateKey
import java.security.PublicKey

class KeyStoreHelper(
    val keyStore: KeyStore
){

    fun generateKeyPairInKeystore(userId: String) {
        try {
            val keyPairGenerator = KeyPairGenerator.getInstance(
                KeyProperties.KEY_ALGORITHM_RSA,
                "AndroidKeyStore"
            )
            val spec = KeyGenParameterSpec.Builder(
                "${userId}_$KEY_ALIAS",
                KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT or
                        KeyProperties.PURPOSE_SIGN or KeyProperties.PURPOSE_VERIFY
            ).run {
                setDigests(KeyProperties.DIGEST_SHA256)
                setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1)
                setSignaturePaddings(KeyProperties.SIGNATURE_PADDING_RSA_PKCS1)
                build()
            }
            keyPairGenerator.initialize(spec)
            keyPairGenerator.generateKeyPair()
        }catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun getPrivateKey(userId: String): PrivateKey? {
        return try {
            keyStore.getKey("${userId}_$KEY_ALIAS", null) as? PrivateKey
        } catch (e: Exception) {
            null
        }
    }

    fun getPublicKey(userId: String): PublicKey? {
        return try {
            keyStore.getCertificate("${userId}_$KEY_ALIAS")?.publicKey
        }catch (e: Exception) {
            null
        }
    }



    companion object{
        const val KEY_ALIAS = "agent_chat_key"
    }

}