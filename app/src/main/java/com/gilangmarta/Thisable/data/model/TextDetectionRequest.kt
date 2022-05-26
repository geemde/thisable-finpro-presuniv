package com.gilangmarta.Thisable.data.model

data class TextDetectionRequest(
    val request: TextDetectionRequestItem
)

data class TextDetectionRequestItem(
    val image: ImageItem,
    val features: List<FeatureItem>
)

data class ImageItem(
    val source: SourceItem
)

data class SourceItem(
    val imageUri: String
)

data class FeatureItem(
    val type: String,
    val maxResults: Int
)