package com.ForSomeoneSpeical.app5.app_sketch.data.repo

import android.util.Log
import com.ForSomeoneSpeical.app5.app_sketch.data.remote.api.ApiService
import com.ForSomeoneSpeical.app5.app_sketch.domain.model.NutritionixFoodItem
import com.ForSomeoneSpeical.app5.app_sketch.domain.model.NutritionixRequest
import com.ForSomeoneSpeical.app5.app_sketch.domain.model.NutritionixSearchResponse
import com.ForSomeoneSpeical.app5.app_sketch.domain.model.Post
import com.ForSomeoneSpeical.app5.app_sketch.domain.repo.UserLogRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserLogRepositoryIml @Inject constructor(
    private val api : ApiService
): UserLogRepository{

    override suspend fun getPost(): Post {
        return api.getPost()
    }

}