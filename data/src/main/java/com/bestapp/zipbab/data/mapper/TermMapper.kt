package com.bestapp.zipbab.data.mapper

import com.bestapp.zipbab.data.model.remote.term.TermsAndConditionResponse
import com.bestapp.zipbab.domain.model.TermsAndCondition

fun TermsAndConditionResponse.toDomain(): TermsAndCondition {
    return TermsAndCondition(
        title = title,
        content = content,
    )
}
