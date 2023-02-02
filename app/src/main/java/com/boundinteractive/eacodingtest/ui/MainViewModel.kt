package com.boundinteractive.eacodingtest.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boundinteractive.eacodingtest.data.RecordLabelRepository
import com.boundinteractive.eacodingtest.data.service.RecordLabelApiResponse
import com.boundinteractive.eacodingtest.ui.data.RecordLabel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val recordLabelRepository: RecordLabelRepository
) : ViewModel() {

    private val _screenState = MutableStateFlow<MainScreenState?>(null)
    val screenState = _screenState.asStateFlow()

    init {
        viewModelScope.launch {
            recordLabelRepository.recordLabels.collectLatest {
                Timber.d("data: api response received")
                when (it) {
                    is RecordLabelApiResponse.Failure -> {
                        Timber.d("data: failure")
                        _screenState.emit(
                            MainScreenState.Failure(
                                it.message
                            )
                        )
                    }
                    is RecordLabelApiResponse.Success -> {
                        Timber.d("data: ${it.data}")
                        _screenState.emit(
                            MainScreenState.Success(
                                it.data
                            )
                        )
                    }
                    else -> {}
                }
            }
        }

        fetchRecordLabel()
    }

    fun fetchRecordLabel() {
        _screenState.value = MainScreenState.Loading
        viewModelScope
            .launch {
                recordLabelRepository
                    .getRecordLabels()
            }
    }

    sealed class MainScreenState {
        class Success(val data: List<RecordLabel>) : MainScreenState()
        object Loading : MainScreenState()
        class Failure(val error: String) : MainScreenState()
    }
}