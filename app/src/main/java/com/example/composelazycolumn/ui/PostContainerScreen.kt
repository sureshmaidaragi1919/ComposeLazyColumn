package com.example.composelazycolumn.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.composelazycolumn.MainActivityViewModel
import com.example.composelazycolumn.data.Post
import com.example.composelazycolumn.data.PostItem

@Composable
fun PostContainerScreen(onItemClick: (PostItem) -> Unit) {

    val viewModel: MainActivityViewModel = viewModel()
    val dataState by viewModel.dataLoadStateFlow.collectAsState(initial = MainActivityViewModel.DataLoadState.Start)

    when (dataState) {
        is MainActivityViewModel.DataLoadState.Failed -> {
            ShowError((dataState as MainActivityViewModel.DataLoadState.Failed).msg)
        }

        MainActivityViewModel.DataLoadState.Loading -> {
            ShowLoader()
        }

        MainActivityViewModel.DataLoadState.Start -> { //do nothing
        }

        is MainActivityViewModel.DataLoadState.Success -> {
            ShowList(
                data = (dataState as MainActivityViewModel.DataLoadState.Success).data as Post,
                onItemClick = onItemClick
            )
        }
    }
}

@Composable
fun ShowLoader() {
    Row(
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(modifier = Modifier.then(Modifier.size(60.dp)))

    }
}


@Composable
fun ShowList(data: Post, onItemClick: (PostItem) -> Unit) {

    LazyColumn(modifier = Modifier.absolutePadding(top = 70.dp)) {
        items(data) { item ->
            ListItemView(item = item, onItemClick = onItemClick)
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListItemView(item: PostItem, onItemClick: (PostItem) -> Unit) {

    Card(elevation = CardDefaults.cardElevation(),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp, horizontal = 10.dp),
        shape = RoundedCornerShape(12.dp),
        onClick = {
            onItemClick(item)
        }) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = item.title.toString(),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.absolutePadding(left = 10.dp, right = 10.dp, top = 10.dp)
            )

            Text(
                text = item.body.toString(),
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.absolutePadding(
                    left = 10.dp, right = 10.dp, top = 10.dp, bottom = 10.dp
                )
            )
        }
    }
}

@Composable
fun ShowError(msg: String) {
    Row(
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = msg,
            style = MaterialTheme.typography.bodySmall,
            fontSize = 16.sp,
            color = Color.Red
        )
    }
}