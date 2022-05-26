package com.gilangmarta.thisable.data.model

import com.google.gson.annotations.SerializedName

data class TextDetectionResponse(
    @SerializedName("responses")
    var responses: List<ResponseItem>
)

data class ResponseItem(
    @SerializedName("fulLTextAnnotation")
    var fullTextAnnotation: FulLTextAnnotationItem
)

data class FulLTextAnnotationItem(
    @SerializedName("text")
    var text: String
)
