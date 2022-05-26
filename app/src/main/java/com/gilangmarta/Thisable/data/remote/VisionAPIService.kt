package com.gilangmarta.Thisable.data.remote

import com.gilangmarta.Thisable.data.model.TextDetectionRequest
import com.gilangmarta.Thisable.data.model.TextDetectionResponse
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