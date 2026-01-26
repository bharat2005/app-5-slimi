package com.ForSomeoneSpeical.app5.app_sketch.domain.repo

import com.ForSomeoneSpeical.app5.app_sketch.domain.model.FinalPlan
import kotlinx.coroutines.flow.Flow

interface AuthReporistory {
    fun saveUserData(finalPlan: FinalPlan) : Flow<Result<Unit>>
}