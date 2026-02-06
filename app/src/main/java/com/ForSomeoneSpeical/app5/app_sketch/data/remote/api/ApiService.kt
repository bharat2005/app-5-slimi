package com.ForSomeoneSpeical.app5.app_sketch.data.remote.api


import com.ForSomeoneSpeical.app5.app_sketch.domain.model.USDAResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @GET("foods/search")
    suspend fun getFoodItems(
        @Query("query") searchText : String,
       @Query ("api_key") apiKey : String = "xgyhoXoq1GXaqXKwKIL6k6MsBeTjGciUoIhRBeda"
    ) : USDAResponse

}


