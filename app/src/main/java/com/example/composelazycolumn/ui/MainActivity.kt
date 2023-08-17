package com.example.composelazycolumn.ui

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.core.content.ContextCompat
import com.example.composelazycolumn.ui.theme.ComposeLazyColumnTheme
import com.example.composelazycolumn.viewmodel.MainActivityViewModel
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
                    TopAppBar(title = {
                        Text(text = "Lazy column")
                    }, navigationIcon = {
                        IconButton(onClick = { }) {
                            Icon(Icons.Filled.Menu, "")
                        }
                    }, scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
                    )
                }, content = {
                    PostContainerScreen { item ->
                        Log.d("SURESH", "title ${item.title}")
                    }
                    it.calculateBottomPadding()

                })

            }
            //check and request for permission
            if (!hasPermissions(
                    this,
                    Manifest.permission.CAMERA,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                RequestForPermission()
            }
        }

    }

    @Composable
    private fun RequestForPermission() {
        val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestMultiplePermissions(),
            onResult = {

                if (it[Manifest.permission.CAMERA] == true) {
                    Toast.makeText(this, "Camera Permission granted", Toast.LENGTH_SHORT).show()

                }

                if (it[Manifest.permission.ACCESS_FINE_LOCATION] == true) {
                    Toast.makeText(this, "Location Permission granted", Toast.LENGTH_SHORT).show()
                }
            })

        SideEffect { //Why side effect check here https://stackoverflow.com/a/68331596/4328589
            launcher.launch(
                arrayOf(
                    Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION
                )
            )

        }

    }

    private fun hasPermissions(context: Context, vararg permissions: String): Boolean =
        permissions.all {
            ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        }

}


