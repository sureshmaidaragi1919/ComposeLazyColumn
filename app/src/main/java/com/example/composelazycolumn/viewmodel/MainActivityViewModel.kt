package com.example.composelazycolumn.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composelazycolumn.repo.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(private val repository: PostRepository) :
    ViewModel() {

    private val _dataLoadStateFlow = MutableStateFlow<DataLoadState>(DataLoadState.Start)
    val dataLoadStateFlow: StateFlow<DataLoadState> get() = _dataLoadStateFlow

    init {
        fetchData()
    }

    private fun fetchData() {
        _dataLoadStateFlow.value = DataLoadState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.execute()
            if (result.isSuccessful) {
                result.body()?.let {
                    Log.d("SURESH","$it")
                    _dataLoadStateFlow.value = DataLoadState.Success(it)
                } ?: kotlin.run {
                    _dataLoadStateFlow.value = DataLoadState.Failed("Failed parse response body")
                }

            } else {
                _dataLoadStateFlow.value = DataLoadState.Failed("Api failed to return response")
            }
        }
    }

    sealed class DataLoadState {
        object Start : DataLoadState()
        object Loading : DataLoadState()
        data class Success(val data: Any) : DataLoadState()
        data class Failed(val msg: String) : DataLoadState()
    }
}