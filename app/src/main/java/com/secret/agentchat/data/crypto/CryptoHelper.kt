package com.secret.agentchat.data.crypto

import com.secret.agentchat.data.keystore.KeyStoreHelper
import com.secret.agentchat.domain.models.Message
import com.secret.agentchat.domain.models.SendMessageParams
import com.secret.agentchat.domain.requests.SendMessageRequest
import com.secret.agentchat.domain.responses.MessageResponse
import com.secret.agentchat.domain.utils.toMessage
import java.util.Base64

class CryptoHelper(
    private val keyStore: KeyStoreHelper
) {
    fun decryptMessages(response: List<MessageResponse>): List<Message> {
        try {
            val privateKey = keyStore.getPrivateKey(response.first().recipient)
            if (privateKey == null) emptyList<Message>()

            return response.map { message ->
                val aesKeyBytes = RSAUtils.decryptWithPrivateKey(
                    Base64.getDecoder().decode(message.encryptedAESKey),
                    privateKey!!
                )

                val aesKey = AESUtils.keyFromBase64(Base64.getEncoder().encodeToString(aesKeyBytes))

                val decryptedText = AESUtils.decrypt(
                    encryptedData = message.encryptedMessage,
                    iv = message.encryptedMessage.substring(
                        0,
                        24
                    ), // IV extraction strategy (adjust if you store IV separately)
                    key = aesKey
                )
                message.toMessage(decryptedText)
            }

        } catch (e: Exception) {
            return emptyList<Message>()
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
                    recipientId = params.recipientId,
                    encryptedMessage = encryptedMessage,
                    encryptedAESKey = encryptedAESKey,
                    signature = signature
                )
            }
        } catch (e: Exception) {
            null
        }
    }
}