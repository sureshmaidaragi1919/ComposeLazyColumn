package com.example.composelazycolumn.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.composelazycolumn.ui.theme.ComposeLazyColumnTheme
import com.example.composelazycolumn.utils.Feature
import com.example.composelazycolumn.utils.featureList

class StarterActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeLazyColumnTheme {
                val topAppBarScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(
                    rememberTopAppBarState()
                )
                Scaffold(topBar = {
                    TopAppBar(title = { Text(text = "Compose Demo") }, navigationIcon = {
                        IconButton(onClick = { println("Clicked") }) {
                            Icon(
                                imageVector = Icons.Default.Menu, contentDescription = ""
                            )
                        }
                    }, scrollBehavior = topAppBarScrollBehavior
                    )

                }, floatingActionButton = {
                    FloatingActionButton(onClick = { /*TODO*/ }) {
                        //FAB content
                    }
                }, floatingActionButtonPosition = FabPosition.End

                ) {
                    it.calculateBottomPadding()
                    val lazyListState = rememberLazyListState()
                    LazyColumn(
                        state = lazyListState,
                        modifier = Modifier
                            .fillMaxWidth()
                            .absolutePadding(left = 10.dp, top = 80.dp, right = 10.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp), //space between each items in list
                    ) {
                        items(featureList) {
                            TypeItemView(it)
                        }
                    }
                }
            }

        }
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TypeItemView(feature: Feature) {
    val context = LocalContext.current
    Box(modifier = Modifier.fillMaxWidth()) {
        Card(elevation = CardDefaults.cardElevation(),
            shape = RoundedCornerShape(18.dp),
            onClick = {
                navigateToActivity(feature, context)
            }) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp), text = feature.name
            )
        }

    }
}


fun navigateToActivity(feature: Feature, context: Context) {
    when (feature.id) {
        0 -> {
            context.startActivity(Intent(context, ListActivity::class.java))
        }

        1 -> {
            context.startActivity(Intent(context, PermissionRequestActivity::class.java))

        }
    }
}
