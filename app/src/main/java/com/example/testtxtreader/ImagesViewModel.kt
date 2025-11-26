package com.example.testtxtreader

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.testtxtreader.model.FileDownloader
import com.example.testtxtreader.model.ImageItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ImagesViewModel(app: Application) : AndroidViewModel(app) {
    private val _items = MutableStateFlow<List<ImageItem>>(emptyList())
    val items = _items.asStateFlow()

    var isFileLoaded by mutableStateOf(false)
        private set

    private val IMAGE_REGEX =
        Regex("""https?://.+?.(jpg|jpeg|png|gif|webp)""", RegexOption.IGNORE_CASE)

    fun load() {
        viewModelScope.launch {
            if (!isFileLoaded) {
                val file =
                    FileDownloader.downloadFile(
                        getApplication(),
                        "https://it-link.ru/test/images.txt"
                    )
                if (file == null) {
                    isFileLoaded = false
                } else {
                    val lines = file.readLines()
                    _items.value = lines.map { line ->
                        val match = IMAGE_REGEX.find(line)
                        when {
                            match != null -> ImageItem.Url(match.value)
                            line.startsWith("http") -> ImageItem.NonImageUrl(line)
                            else -> ImageItem.NotALink(line)
                        }
                    }
                    isFileLoaded = true
                }
            }
        }
    }
}