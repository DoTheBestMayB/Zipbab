package com.bestapp.zipbab.ui.recruitment

import androidx.annotation.IntRange

data class RecruitmentState(
    @IntRange(MIN_STEP.toLong(), MAX_STEP.toLong()) val currentStep: Int = MIN_STEP,
) {

    fun getStepInfo(): List<StepInfo> {
        val steps = mutableListOf<StepInfo>()

        for (i in MIN_STEP..currentStep) {
            steps.add(StepInfo(i, true))
        }
        for (i in currentStep + 1..MAX_STEP) {
            steps.add(StepInfo(i, false))
        }

        return steps.toList()
    }

    companion object {
        const val MIN_STEP = 0
        const val MAX_STEP = 5
    }
}
