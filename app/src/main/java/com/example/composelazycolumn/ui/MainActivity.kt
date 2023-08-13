package com.example.composelazycolumn.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import com.example.composelazycolumn.viewmodel.MainActivityViewModel
import com.example.composelazycolumn.ui.theme.ComposeLazyColumnTheme
import dagger.hilt.android.AndroidEntryPoint

@OptIn(ExperimentalMaterial3Api::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainActivityViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeLazyColumnTheme {
                Scaffold(topBar = {
                    TopAppBar(

                        title = {
                            Text(text = "Lazy column")
                        },
                        navigationIcon = {
                            IconButton(onClick = { }) {
                                Icon(Icons.Filled.Menu, "")
                            }
                        },
                        scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

                        )
                }, content = {
                    PostContainerScreen(onItemClick = {
                        Log.d("SURESH", "Fucked asshole ${it.title}")
                    })
                    it.calculateBottomPadding()

                })


            }
        }

    }


}


