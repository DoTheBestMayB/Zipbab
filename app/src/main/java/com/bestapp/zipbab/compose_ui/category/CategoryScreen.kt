package com.bestapp.zipbab.compose_ui.category

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bestapp.zipbab.domain.model.user.MeetType

/**
 * @param meetType 모임 종류
 * @param label 선택된 카테고리 이름. 선택되지 않으면 null
 */
@Composable
fun CategoryScreenRoot(
    meetType: MeetType,
    modifier: Modifier = Modifier,
    label: String? = null,
) {

}
