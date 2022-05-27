package com.gilangmarta.thisable.data.model

import com.google.gson.annotations.SerializedName

data class TextDetectionResponse(
    @SerializedName("responses")
    var responses: List<ResponseItem>
)

data class ResponseItem(
    @SerializedName("fullTextAnnotation")
    var fullTextAnnotation: fullTextAnnotationItem,
    @SerializedName("error")
    val error: ErrorItem
)

data class fullTextAnnotationItem(
    @SerializedName("text")
    var text: String
)

data class ErrorItem(
    @SerializedName("code")
    var code: Int,
    @SerializedName("message")
    var message: String
)
