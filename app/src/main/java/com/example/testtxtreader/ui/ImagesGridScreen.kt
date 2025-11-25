package com.example.testtxtreader.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.testtxtreader.model.ImageItem

@Composable
fun ImagesGridScreen(
    items: List<ImageItem>,
    onImageClick: (Int) -> Unit,
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val cellMinWidth = 100.dp
    val columns = (screenWidth / cellMinWidth).toInt().coerceAtLeast(1)

    LazyVerticalGrid(
        columns = GridCells.Fixed(columns),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        itemsIndexed(items) { index, item ->
            when (item) {
                is ImageItem.Url -> AsyncImage(
                    model = item.url,
                    contentDescription = null,
                    modifier = Modifier
                        .aspectRatio(1f)
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .clickable { onImageClick(index) }
                )
                is ImageItem.NonImageUrl -> Box(
                    Modifier
                        .aspectRatio(1f)
                        .fillMaxWidth()
                        .background(Color.Gray)
                        .clickable { /* noop */ },
                    contentAlignment = Alignment.Center,
                ) {
                    Text("URL", color = Color.White, textAlign = TextAlign.Center)
                }
                is ImageItem.NotALink -> Box(
                    Modifier
                        .aspectRatio(1f)
                        .fillMaxWidth()
                        .background(Color.LightGray),
                    contentAlignment = Alignment.Center,
                ) {
                    Text("?", color = Color.Black, textAlign = TextAlign.Center)
                }
            }
        }
    }
}