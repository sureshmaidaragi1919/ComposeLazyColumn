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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat

class PermissionRequestActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val scaffoldState =
                remember { SnackbarHostState() } // Todo find why show snackbar not working

            Scaffold(snackbarHost = { scaffoldState }, topBar = {
                TopAppBar(title = { Text(text = "") }, navigationIcon = {
                    IconButton(onClick = {
                        onBackPressed()
                    }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "")
                    }
                })
            }) { content ->
                content.calculateTopPadding()
//check and request for permission
                if (!hasPermissions(
                        this, Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION
                    )
                ) {
                    RequestForPermission()
                } else {
                    Toast.makeText(LocalContext.current,"Permission's already given",Toast.LENGTH_SHORT).show()
                    LaunchedEffect(key1 = "") {
                        val snackbarResult = scaffoldState.showSnackbar(
                            message = "This is your message",
                            actionLabel = "Do something.",
                            duration = SnackbarDuration.Long
                        )
                        when (snackbarResult) {
                            SnackbarResult.Dismissed -> Log.d("SnackbarDemo", "Dismissed")
                            SnackbarResult.ActionPerformed -> Log.d(
                                "SnackbarDemo",
                                "Snackbar's button clicked"
                            )

                            else -> {}
                        }

                    }


                }
            }
        }
    }

    @Composable
    private fun RequestForPermission() {
        val launcher =
            rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestMultiplePermissions(),
                onResult = {

                    if (it[Manifest.permission.CAMERA] == true) {
                        Toast.makeText(this, "Camera Permission granted", Toast.LENGTH_SHORT).show()

                    }

                    if (it[Manifest.permission.ACCESS_FINE_LOCATION] == true) {
                        Toast.makeText(this, "Location Permission granted", Toast.LENGTH_SHORT)
                            .show()
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