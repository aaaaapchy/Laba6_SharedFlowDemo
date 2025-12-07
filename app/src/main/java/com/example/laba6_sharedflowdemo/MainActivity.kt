package com.example.laba6_sharedflowdemo

import android.os.Bundle
import android.util.Log.i
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.laba6_sharedflowdemo.ui.theme.Laba6_SharedFlowDemoTheme
import kotlinx.coroutines.flow.SharedFlow

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Laba6_SharedFlowDemoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ScreenSetup(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun ScreenSetup(
    modifier: Modifier = Modifier,
    viewModel: DemoViewModel = viewModel()
) {
    MainScreen(
        modifier = modifier,
        sharedFlow = viewModel.sharedFlow
    )
}

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    sharedFlow: SharedFlow<Int>
) {
    val messages = remember { mutableStateListOf<Int>() }

    LaunchedEffect(key1 = Unit) {
        sharedFlow.collect {
            println("Collecting $it")
            messages.add(it)
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(messages) { item ->
            Text(
                text = "Collected Value = $item",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Laba6_SharedFlowDemoTheme {
        ScreenSetup()
    }
}