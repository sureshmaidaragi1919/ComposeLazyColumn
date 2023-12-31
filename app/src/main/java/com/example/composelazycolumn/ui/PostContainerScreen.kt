package com.example.composelazycolumn.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.composelazycolumn.R
import com.example.composelazycolumn.data.Post
import com.example.composelazycolumn.data.PostItem
import com.example.composelazycolumn.viewmodel.MainActivityViewModel

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
                onItemClick = onItemClick,
                viewModel = viewModel
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
        CircularProgressIndicator(modifier = Modifier.size(60.dp))
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ShowList(data: Post, onItemClick: (PostItem) -> Unit, viewModel: MainActivityViewModel) {

    var sortedData =
        remember { data.sortedBy { it.title } } //we can move this out of compose itself or put into viewmodel or repo
    var refreshing by remember { mutableStateOf(false) }

    val state = rememberPullRefreshState(refreshing = refreshing, onRefresh = {
        refreshing = true
        viewModel.refresh()
        refreshing = false

    })

    Box(
        modifier = Modifier.pullRefresh(state)
    ) {
        LazyColumn(
            modifier = Modifier.absolutePadding(top = 70.dp),
            contentPadding = PaddingValues(12.dp),//margin at start and end
            verticalArrangement = Arrangement.spacedBy(10.dp), //space between each items in list
        ) {
            items(sortedData) { item ->
                ListItemView(item = item, onItemClick = onItemClick)
            }
        }
        PullRefreshIndicator(
            refreshing = refreshing,
            state = state,
            modifier = Modifier.align(Alignment.TopCenter)
        )

    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ListItemView(item: PostItem, onItemClick: (PostItem) -> Unit) {

    Card(elevation = CardDefaults.cardElevation(), shape = RoundedCornerShape(12.dp), onClick = {
        onItemClick(item)
    }) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row {
                Image(
                    painter = painterResource(id = R.mipmap.ic_flower),
                    contentDescription = "",
                    modifier = Modifier.absolutePadding(left = 10.dp, right = 6.dp, top = 12.dp)
                )
                Text(
                    text = item.title.toString(),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.absolutePadding(right = 10.dp, top = 10.dp)
                )

            }

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
private fun ShowError(msg: String) {
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