package com.gilangmarta.thisable.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gilangmarta.thisable.data.model.TextDetectionRequest
import com.gilangmarta.thisable.data.model.TextDetectionResponse
import com.gilangmarta.thisable.data.remote.APIResponse
import com.gilangmarta.thisable.data.repository.VisionApiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VisionAPIViewModel @Inject constructor(private val repository: VisionApiRepository): ViewModel() {
    fun textDetection(apiKey: String, textDetectionRequest: TextDetectionRequest) : LiveData<APIResponse<TextDetectionResponse>> {
        val response = MutableLiveData<APIResponse<TextDetectionResponse>>()
        viewModelScope.launch {
            repository.textDetection(apiKey, textDetectionRequest).collect {
                response.postValue(it)
            }
        }
        return response
    }
}