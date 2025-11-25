package com.example.testtxtreader.model

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.net.URL

object FileDownloader {
    suspend fun downloadFile(context: Context, url: String): File? {
        val file = File(context.filesDir, "images.txt")
        return try {
            withContext(Dispatchers.IO) {
                URL(url).openStream().use { input ->
                    file.outputStream().use { output ->
                        input.copyTo(output)
                    }
                }
            }
            file
        } catch (e: Exception) {
            if (file.exists()) file else null
        }
    }
}