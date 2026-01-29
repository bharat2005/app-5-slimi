package com.ForSomeoneSpeical.app5.app_sketch.data.repo

import com.ForSomeoneSpeical.app5.app_sketch.domain.model.NutritionixFoodItem
import com.ForSomeoneSpeical.app5.app_sketch.domain.repo.UserLogRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserLogRepositoryIml @Inject constructor(

): UserLogRepository{
    override fun searchFoodItems(query: String): Flow<Result<List<NutritionixFoodItem>>> = flow {
        emit(Result.success(emptyList()))
    }
}