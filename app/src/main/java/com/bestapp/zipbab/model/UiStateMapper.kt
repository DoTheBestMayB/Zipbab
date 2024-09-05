package com.bestapp.zipbab.model

import com.bestapp.zipbab.args.ProfileEditArgs
import com.bestapp.zipbab.data.model.UploadStateEntity
import com.bestapp.zipbab.data.model.local.GalleryImageInfo
import com.bestapp.zipbab.data.model.local.SignOutEntity
import com.bestapp.zipbab.data.model.remote.FilterResponse
import com.bestapp.zipbab.data.model.remote.LoginResponse
import com.bestapp.zipbab.data.model.remote.MeetingResponse
import com.bestapp.zipbab.data.model.remote.NotificationTypeResponse
import com.bestapp.zipbab.data.model.remote.PlaceLocation
import com.bestapp.zipbab.data.model.remote.PostResponse
import com.bestapp.zipbab.data.model.remote.Review
import com.bestapp.zipbab.data.model.remote.SignUpResponse
import com.bestapp.zipbab.data.model.remote.TermInfoResponse
import com.bestapp.zipbab.data.model.remote.UserResponse
import com.bestapp.zipbab.ui.mettinginfo.MemberInfo
import com.bestapp.zipbab.ui.profileedit.ProfileEditUiState
import com.bestapp.zipbab.ui.profilepostimageselect.model.PostGalleryUiState
import com.bestapp.zipbab.ui.profilepostimageselect.model.SelectedImageUiState
import com.bestapp.zipbab.ui.recruitment.viewpager.categoryselect.FoodCategory
import com.bestapp.zipbab.ui.signup.SignUpState

// Data -> UiState

fun SignOutEntity.toUi(): SignOutState {
    return when (this) {
        SignOutEntity.Fail -> SignOutState.Fail
        SignOutEntity.IsNotAllowed -> SignOutState.IsNotAllowed
        SignOutEntity.Success -> SignOutState.Success
    }
}

fun FilterResponse.Cost.toUi() = FilterUiState.CostUiState(
    name = name,
    icon = icon,
    type = type,
)

fun FilterResponse.Food.toUi() = FilterUiState.FoodUiState(
    icon = icon,
    name = name,
)

fun FilterResponse.Food.toCategory() = FoodCategory(
    name = name,
    isSelected = false,
)

fun MeetingResponse.toUi() = MeetingUiState(
    meetingDocumentID = meetingDocumentID,
    title = title,
    titleImage = titleImage,
    placeLocationUiState = placeLocation.toUi(),
    time = time,
    recruits = recruits,
    description = description,
    mainMenu = mainMenu,
    costValueByPerson = costValueByPerson,
    costTypeByPerson = costTypeByPerson,
    hostUserDocumentID = hostUserDocumentID,
    members = members,
    pendingMembers = pendingMembers,
    attendanceCheck = attendanceCheck,
    activation = activation
)

fun PlaceLocation.toUi() = PlaceLocationUiState(
    locationAddress = locationAddress,
    locationLat = locationLat,
    locationLong = locationLong,
)

fun PostResponse.toUi() = PostUiState(
    postDocumentID = postDocumentID,
    images = images,
    state = UploadState.Default(
        tempPostDocumentID = postDocumentID
    ),
)

fun Review.toUi() = ReviewUiState(
    id = id,
    votingPoint = votingPoint,
)

fun TermInfoResponse.toUi() = TermInfoState(
    id = id,
    content = content,
    date = date,
)

fun UserResponse.toUi() = UserUiState(
    userDocumentID = userDocumentID,
    nickname = nickname,
    id = id,
    pw = pw,
    profileImage = profileImage,
    temperature = temperature,
    meetingCount = meetingCount,
    notificationUiState = notifications.map { it.toUi() },
    meetingReviews = meetingReviews,
    postDocumentIds = posts,
    placeLocationUiState = placeLocation.toUi(),
)

fun UploadStateEntity.toArgs(): UploadState {
    return when (this) {
        is UploadStateEntity.Fail -> UploadState.Fail(
            tempPostDocumentID = tempPostDocumentID
        )

        is UploadStateEntity.Pending -> UploadState.Pending(
            tempPostDocumentID = tempPostDocumentID
        )

        is UploadStateEntity.ProcessImage -> UploadState.InProgress(
            tempPostDocumentID = tempPostDocumentID,
            currentProgressOrder = currentProgressOrder,
            maxOrder = maxOrder,
        )

        is UploadStateEntity.ProcessPost -> UploadState.ProcessPost(
            tempPostDocumentID = tempPostDocumentID,
        )

        is UploadStateEntity.SuccessPost -> UploadState.SuccessPost(
            tempPostDocumentID = tempPostDocumentID,
            postDocumentID = postDocumentID,
        )
    }
}

fun LoginResponse.toUi(): LoginResult {
    return when (this) {
        LoginResponse.Fail -> LoginResult.Fail
        is LoginResponse.Success -> LoginResult.Success(this.userDocumentID)
    }
}

fun SignUpResponse.toUi(): SignUpState {
    return when (this) {
        SignUpResponse.DuplicateEmail -> SignUpState.DuplicateEmail
        SignUpResponse.Fail -> SignUpState.Fail
        is SignUpResponse.Success -> SignUpState.Success(this.userDocumentID)
    }
}

fun NotificationTypeResponse.toUi(): NotificationUiState.UserNotification {
    return NotificationUiState.UserNotification(dec = "", uploadDate = uploadDate)
}

fun GalleryImageInfo.toUi() = GalleryImage(
    uri = uri,
    name = name,
    orderId = orderId,
)

// UiState -> UiState
fun GalleryImageInfo.toPostGalleryState() = PostGalleryUiState(
    uri = uri,
    name = name,
)

fun PostGalleryUiState.toSelectUiState() = SelectedImageUiState(
    uri = uri,
    name = name,
    order = order,
)

fun SelectedImageUiState.toGalleryUiState() = PostGalleryUiState(
    uri = uri,
    name = name,
    order = order,
)

fun UserUiState.toMemberInfo(isHost: Boolean) = MemberInfo(
    userDocumentID = userDocumentID,
    nickname = nickname,
    profileImage = profileImage,
    isHost = isHost,
)

// Args -> UiState

fun ProfileEditArgs.toUi() = ProfileEditUiState(
    userDocumentID = userDocumentID,
    nickname = nickname,
    profileImage = profileImage,
)