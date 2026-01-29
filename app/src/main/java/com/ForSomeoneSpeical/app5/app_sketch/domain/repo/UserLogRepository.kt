package com.ForSomeoneSpeical.app5.app_sketch.domain.repo

interface UserLogRepository {
    suspend fun searchFoodItems(query : String)
}