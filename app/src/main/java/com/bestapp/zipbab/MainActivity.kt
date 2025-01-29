package com.bestapp.zipbab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.compose.rememberNavController
import com.bestapp.zipbab.compose_ui.home.CategoryUiState
import com.bestapp.zipbab.navigation.NavigationRoot
import com.bestapp.zipbab.theme.ZipbabTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        var userPrivateUiState: UserPrivateUiState by mutableStateOf(UserPrivateUiState.Loading)
        var categoryUiState: CategoryUiState by mutableStateOf(CategoryUiState.Loading)

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.userPrivateUiState.collect { state ->
                        userPrivateUiState = state
                    }
                }
                launch {
                    viewModel.categoryUiState.collect { state ->
                        categoryUiState = state
                    }
                }
            }
        }

        splashScreen.setKeepOnScreenCondition {
            userPrivateUiState is UserPrivateUiState.Loading || categoryUiState is CategoryUiState.Loading
        }


        setContent {
            ZipbabTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavigationRoot(
                        navController = navController,
                        isLoggedIn = userPrivateUiState is UserPrivateUiState.LoggedIn,
                    )
                }
            }
        }
    }
}
