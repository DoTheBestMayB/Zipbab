package com.bestapp.zipbab.home

interface AlertUiState {
    data object Loading : AlertUiState

    data object Empty: AlertUiState

    data object Exist: AlertUiState
}
