package com.gilangmarta.thisable.data.remote

import com.gilangmarta.thisable.data.model.TextDetectionRequest
import com.gilangmarta.thisable.data.model.TextDetectionResponse
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface VisionAPIService {
    @POST("images:annotate")
    suspend fun textDetection(
        @Query("key") apiKey: String,
        @Body textDetectionRequest: TextDetectionRequest
    ): TextDetectionResponse
}