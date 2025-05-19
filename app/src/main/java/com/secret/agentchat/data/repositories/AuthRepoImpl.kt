package com.secret.agentchat.data.repositories

import com.secret.agentchat.data.api.AuthService
import com.secret.agentchat.data.crypto.AESUtils
import com.secret.agentchat.data.datastore.SharedPref
import com.secret.agentchat.data.keystore.KeyStoreHelper
import com.secret.agentchat.domain.models.RegisterData
import com.secret.agentchat.domain.repositories.AuthRepo
import com.secret.agentchat.domain.requests.LoginRequest
import com.secret.agentchat.domain.requests.RegisterRequest
import com.secret.agentchat.domain.responses.LoginResponse
import com.secret.agentchat.domain.responses.RegisterResponse
import org.bson.types.ObjectId

class AuthRepoImpl(
    private val auth: AuthService,
    private val sharedPref: SharedPref,
    private val keyStore: KeyStoreHelper
) : AuthRepo {

    override suspend fun login(request: LoginRequest): LoginResponse? {
        try {
            val response = auth.login(request)
            if (response.isSuccessful){
                response.body()?.let {
                    sharedPref.put(SharedPref.TOKEN_KEY, it.token)
                    sharedPref.put(SharedPref.USER_ID_KEY, it.userId)
                    return it
                }
            }
            return null
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    override suspend fun register(data: RegisterData): RegisterResponse? {
        try {
            val userId = ObjectId().toHexString()
            keyStore.generateKeyPairInKeystore(userId)
            val publicKey = keyStore.getPublicKey(userId)
            publicKey?.let {
                val request = RegisterRequest(
                    id = userId,
                    username = data.username,
                    email = data.email,
                    password = data.password,
                    publicKey = AESUtils.toBase64(publicKey),
                )
                val response = auth.register(request)
                if (response.isSuccessful){
                    response.body()?.let {
                        sharedPref.put(SharedPref.TOKEN_KEY, it.token)
                        sharedPref.put(SharedPref.USER_ID_KEY, userId)
                        return it
                    }
                }
            }
            return null
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }
}
