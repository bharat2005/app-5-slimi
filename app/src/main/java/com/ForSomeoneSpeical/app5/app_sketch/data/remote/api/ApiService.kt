package com.ForSomeoneSpeical.app5.app_sketch.data.remote.api

import com.ForSomeoneSpeical.app5.app_sketch.domain.model.NutritionixFoodItem
import com.ForSomeoneSpeical.app5.app_sketch.domain.model.NutritionixRequest
import com.ForSomeoneSpeical.app5.app_sketch.domain.model.NutritionixSearchResponse
import com.ForSomeoneSpeical.app5.app_sketch.domain.model.Post
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {

    @GET("posts/1")
    suspend fun getPost() : Post

}


