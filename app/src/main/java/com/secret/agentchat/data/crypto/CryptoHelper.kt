package com.secret.agentchat.data.crypto

import com.secret.agentchat.data.keystore.KeyStoreHelper
import com.secret.agentchat.domain.models.SendMessageParams
import com.secret.agentchat.domain.requests.SendMessageRequest
import com.secret.agentchat.domain.responses.MessageResponse
import java.time.Instant
import java.util.Base64

class CryptoHelper(
    private val keyStore: KeyStoreHelper
) {
fun decryptMessage(message: MessageResponse): String? {
        try {
            val privateKey = keyStore.getPrivateKey(message.recipient)
            privateKey?.let {
                val aesKeyBytes = RSAUtils.decryptWithPrivateKey(
                    Base64.getDecoder().decode(message.encryptedAESKey),
                    privateKey
                )

                val aesKey = AESUtils.keyFromBase64(Base64.getEncoder().encodeToString(aesKeyBytes))

                val decryptedText = AESUtils.decrypt(
                    encryptedData = message.encryptedMessage,
                    iv = message.encryptedMessage.substring(0, 24),
                    key = aesKey
                )
                return decryptedText
            } ?: return null

        } catch (e: Exception) {
            return null
        }
    }

    fun encryptMessage(params: SendMessageParams): SendMessageRequest? {
        return try {
            val privateKey = keyStore.getPrivateKey(userId = params.userId)
            privateKey?.let { key ->
                val aesKey = AESUtils.generateAESKey()

                val (encryptedMessage, iv) = AESUtils.encrypt(params.message, aesKey)

                val encryptedAESKeyBytes = RSAUtils.encryptWithPublicKey(
                    aesKey.encoded,
                    RSAUtils.publicKeyFromBase64(params.recipientPublicKey)
                )
                val encryptedAESKey = Base64.getEncoder().encodeToString(encryptedAESKeyBytes)

                val signature = SignatureUtils.sign(encryptedMessage, key)

                SendMessageRequest(
                    chatId = params.chatId,
                    senderId = params.userId,
                    encryptedMessage = encryptedMessage,
                    encryptedAESKey = encryptedAESKey,
                    signature = signature,
                    ephemeral = false,
                    createdAt = Instant.now().toEpochMilli().toString()
                )
            }
        } catch (e: Exception) {
            null
        }
    }
}