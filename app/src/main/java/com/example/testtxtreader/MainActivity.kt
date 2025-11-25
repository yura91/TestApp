package com.example.testtxtreader

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.testtxtreader.model.ImageItem
import com.example.testtxtreader.ui.ImageViewerScreen
import com.example.testtxtreader.ui.ImagesGridScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val vm: ImagesViewModel = viewModel()

            LaunchedEffect(Unit) { vm.load() }

            NavHost(navController, startDestination = "grid") {
                composable("grid") {
                    val items by vm.items.collectAsState()
                    ImagesGridScreen(items) { index ->
                        navController.navigate("viewer/$index")
                    }
                }
                composable(
                    route = "viewer/{startIndex}",
                    arguments = listOf(navArgument("startIndex") { type = NavType.IntType })
                ) { backStackEntry ->
                    val items by vm.items.collectAsState()
                    val images =
                        items.mapNotNull { if (it is ImageItem.Url) it.url else null }
                    val startIndex =
                        backStackEntry.arguments?.getInt("startIndex") ?: 0

                    if (images.isNotEmpty()) {
                        ImageViewerScreen(
                            images = images,
                            startIndex =
                            images.indexOf((items[startIndex] as? ImageItem.Url)?.url)
                                .coerceAtLeast(0),
                            onBack = { navController.popBackStack() },
                        )
                    }
                }
            }
        }
    }
}