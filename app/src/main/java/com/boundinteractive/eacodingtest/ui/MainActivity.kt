package com.boundinteractive.eacodingtest.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.boundinteractive.eacodingtest.ui.MainViewModel
import com.boundinteractive.eacodingtest.ui.screens.FailureScreen
import com.boundinteractive.eacodingtest.ui.screens.LoadingScreen
import com.boundinteractive.eacodingtest.ui.screens.SuccessScreen
import com.boundinteractive.eacodingtest.ui.theme.EACodingTestTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EACodingTestTheme {
                // A surface container using the 'background' color from the theme
                Scaffold(
                    floatingActionButton = {
                        FloatingActionButton(onClick = { mainViewModel.fetchRecordLabel() }) {
                            Icon(Icons.Filled.Refresh, "Refresh")
                        }
                    }
                ) {
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(it),
                        color = MaterialTheme.colors.background
                    ) {
                        val screenState by mainViewModel.screenState.collectAsState()

                        with(screenState) {
                            when(this) {
                                is MainViewModel.MainScreenState.Failure -> FailureScreen(message = this.error)
                                is MainViewModel.MainScreenState.Loading -> LoadingScreen()
                                is MainViewModel.MainScreenState.Success -> SuccessScreen(data = this.data)
                                else -> {}
                            }
                        }
                    }
                }
            }
        }
    }
}