package com.bharat.app5.feature_auth.domain.repository

import com.bharat.app5.feature_auth.domain.model.UserDetails
import com.google.firebase.auth.AuthCredential
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun firebaseSignInWithGoogle(credential : AuthCredential) : Flow<Result<String>>

    suspend fun saveUsersDetails(uid : String, userDetails: UserDetails) : Flow<Result<Unit>>
}