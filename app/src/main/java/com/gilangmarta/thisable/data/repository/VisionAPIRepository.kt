package com.gilangmarta.thisable.data.repository

import com.gilangmarta.thisable.data.model.TextDetectionRequest
import com.gilangmarta.thisable.data.model.TextDetectionResponse
import com.gilangmarta.thisable.data.remote.APIResponse
import com.gilangmarta.thisable.data.source.VisionApiDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VisionApiRepository @Inject constructor(private val dataSource: VisionApiDataSource) {

    suspend fun textDetection(
        apiKey: String,
        textDetectionRequest: TextDetectionRequest
    ): Flow<APIResponse<TextDetectionResponse>> {
        return dataSource.textDetection(apiKey, textDetectionRequest).flowOn(Dispatchers.IO)
    }

}