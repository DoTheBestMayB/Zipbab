package com.bestapp.zipbab.ui.notification

sealed class NotificationType { //muti-recyclerView 구현을 위한 클래스

    data class MainNotification( //관리자 전용 알림 데이터 형식
        val dec: String,
        val uploadDate: String
    ) : NotificationType()

    data class UserNotification( //유저 전용 알림 데이터 형식
        val dec: String,
        val uploadDate: String
    ) : NotificationType()
}