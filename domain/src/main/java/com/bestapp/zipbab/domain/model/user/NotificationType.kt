package com.bestapp.zipbab.domain.model.user

sealed interface NotificationType {

    data class FlashMeetRequest(
        val meetId: String = "",
        val requestUserId: String = "",
        val joinMessage: String = "",
    ): NotificationType

    data class FlashMeetApproval(
        val meetId: String = "",
    ): NotificationType

    data class FlashMeetReject(
        val meetId: String = "",
        val reason: String = "",
    ): NotificationType

    data object None: NotificationType
}
