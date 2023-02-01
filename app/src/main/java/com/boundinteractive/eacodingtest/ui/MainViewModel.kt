package com.boundinteractive.eacodingtest.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boundinteractive.eacodingtest.data.RecordLabelRepository
import com.boundinteractive.eacodingtest.ui.data.RecordLabel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val recordLabelRepository: RecordLabelRepository
) : ViewModel() {

    val screenState = MutableStateFlow<MainScreenState>(MainScreenState.Loading(true))

    fun fetchRecordLabel() {
        screenState.value = MainScreenState.Loading(true)
        viewModelScope
            .launch {
                recordLabelRepository
                    .getRecordLabels()
                    .also {
                        screenState.value = MainScreenState.Loading(false)
                    }
                /*.doOnSuccess {
                    Log.d("data", it.toString())
                    screenState.value = MainScreenState.Success(it)
                }
                .doOnError {
                    screenState.value = MainScreenState.Failure(it.toFriendlyMessage())
                }*/
            }
    }

    sealed class MainScreenState {
        class Success(val data: List<RecordLabel>) : MainScreenState()
        class Loading(val isLoading: Boolean) : MainScreenState()
        class Failure(val error: String) : MainScreenState()
    }
}