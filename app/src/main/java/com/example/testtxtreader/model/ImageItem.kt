package com.example.testtxtreader.model

sealed class ImageItem {
    data class Url(val url: String): ImageItem()
    data class NonImageUrl(val url: String): ImageItem()
    data class NotALink(val text: String): ImageItem()
}