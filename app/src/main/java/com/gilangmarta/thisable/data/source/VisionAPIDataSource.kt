package com.gilangmarta.thisable.data.source

import com.gilangmarta.thisable.data.model.TextDetectionRequest
import com.gilangmarta.thisable.data.model.TextDetectionResponse
import com.gilangmarta.thisable.data.remote.APIResponse
import com.gilangmarta.thisable.data.remote.VisionAPIService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VisionApiDataSource @Inject constructor(private val service: VisionAPIService) {

    suspend fun textDetection(
        apiKey: String,
        textDetectionRequest: TextDetectionRequest
    ): Flow<APIResponse<TextDetectionResponse>> {
        return flow {
            try {
                emit(APIResponse.Loading)
                val response = service.textDetection(apiKey, textDetectionRequest)
                emit(APIResponse.Success(response))
            } catch (ex: Exception) {
                emit(APIResponse.Error(ex.message.toString()))
            }
        }
    }

}
