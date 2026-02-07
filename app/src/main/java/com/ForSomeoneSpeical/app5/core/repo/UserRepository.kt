package com.ForSomeoneSpeical.app5.core.repo

import com.ForSomeoneSpeical.app5.app_sketch.domain.model.FinalPlanDTO
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.snapshots
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import okhttp3.Dispatcher
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val firestore: FirebaseFirestore
){
    val userUid = "user1"
    val appScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    val userDataState : StateFlow<FinalPlanDTO?> = firestore
        .collection("users")
        .document(userUid)
        .snapshots()
        .map { snapshot ->
            snapshot.toObject(FinalPlanDTO::class.java)
        }
        .stateIn(
            appScope,
            SharingStarted.WhileSubscribed(5000),
            null
        )


}
















