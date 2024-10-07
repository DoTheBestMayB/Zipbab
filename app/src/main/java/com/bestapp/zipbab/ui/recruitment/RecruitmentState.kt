package com.bestapp.zipbab.ui.recruitment

import androidx.annotation.IntRange

data class RecruitmentState(
    @IntRange(MIN_STEP.toLong(), MAX_STEP.toLong()) val currentStep: Int = MIN_STEP,
    val steps: List<RecruitViewPagerStep> = List(MAX_STEP + 1) { step ->
        val isProcess = step == MIN_STEP
        RecruitViewPagerStep(step, isProcess)
    },
) {
    companion object {
        const val MIN_STEP = 0
        const val MAX_STEP = 5
    }
}
