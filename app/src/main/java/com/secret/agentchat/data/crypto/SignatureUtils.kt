package com.secret.agentchat.data.crypto

import java.security.PrivateKey
import java.security.PublicKey
import java.security.Signature
import java.util.Base64

object SignatureUtils {

    fun sign(data: String, privateKey: PrivateKey): String {
        val signature = Signature.getInstance("SHA256withRSA")
        signature.initSign(privateKey)
        signature.update(data.toByteArray(Charsets.UTF_8))
        return Base64.getEncoder().encodeToString(signature.sign())
    }

    fun verify(data: String, signatureBase64: String, publicKey: PublicKey): Boolean {
        val signature = Signature.getInstance("SHA256withRSA")
        signature.initVerify(publicKey)
        signature.update(data.toByteArray(Charsets.UTF_8))
        val signatureBytes = Base64.getDecoder().decode(signatureBase64)
        return signature.verify(signatureBytes)
    }
}