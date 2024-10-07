package com.bestapp.zipbab.model

import com.bestapp.zipbab.args.FilterArgs

sealed interface FilterUiState {

    data class FoodUiState(
        val icon: String,
        val name: String,
    ) : FilterUiState {

        fun toArgs() = FilterArgs.FoodArgs(
            icon = icon,
            name = name,
        )
    }

    data class CostUiState(
        val icon: String,
        val name: String,
        val type: Int,
    ) : FilterUiState {

        fun toArgs() = FilterArgs.CostArgs(
            icon = icon,
            name = name,
            type = type,
        )
    }
}