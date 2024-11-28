package com.bestapp.zipbab.model

import android.net.Uri
import com.bestapp.zipbab.args.ProfileEditArgs
import com.bestapp.zipbab.data.model.local.SignOutEntity
import com.bestapp.zipbab.data.model.remote.PlaceLocation
import com.bestapp.zipbab.data.model.remote.Review
import com.bestapp.zipbab.data.model.remote.TermInfoResponse
import com.bestapp.zipbab.data.model.remote.auth.AuthStateDto
import com.bestapp.zipbab.data.model.remote.auth.SignUpResponse
import com.bestapp.zipbab.data.remote.upload.UploadStateDto
import com.bestapp.zipbab.domain.model.GalleryImage
import com.bestapp.zipbab.ui.mettinginfo.MemberInfo
import com.bestapp.zipbab.ui.profileedit.ProfileEditUiState
import com.bestapp.zipbab.ui.profilepostimageselect.model.PostGalleryUiState
import com.bestapp.zipbab.ui.profilepostimageselect.model.SelectedImageUiState
import com.bestapp.zipbab.ui.signup.SignUpState

// Data -> UiState

fun SignOutEntity.toUi(): SignOutState {
    return when (this) {
        SignOutEntity.Fail -> SignOutState.Fail
        SignOutEntity.IsNotAllowed -> SignOutState.IsNotAllowed
        SignOutEntity.Success -> SignOutState.Success
    }
}


fun PlaceLocation.toUi() = PlaceLocationUiState(
    locationAddress = locationAddress,
    locationLat = locationLat,
    locationLong = locationLong,
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

fun AuthStateDto.toUi(): VerifyState {
    return when(this) {
        AuthStateDto.AlreadyUsedEmail -> VerifyState.AlreadyUsedEmail
        AuthStateDto.Fail -> VerifyState.Fail
        AuthStateDto.FailAtSendVerificationEmail -> VerifyState.FailAtSendVerificationEmail
        AuthStateDto.Success -> VerifyState.Success
        AuthStateDto.PasswordTooShort -> VerifyState.PasswordTooShort
    }
}

fun UploadStateDto.toArgs(): UploadState {
    return when (this) {
        is UploadStateDto.Fail -> UploadState.Fail(
            tempPostDocumentID = tempPostDocumentId
        )

        is UploadStateDto.Pending -> UploadState.Pending(
            tempPostDocumentID = tempPostDocumentId
        )

        is UploadStateDto.ProcessImage -> UploadState.InProgress(
            tempPostDocumentID = tempPostDocumentId,
            currentProgressOrder = currentProgressOrder,
            maxOrder = maxOrder,
        )

        is UploadStateDto.ProcessPost -> UploadState.ProcessPost(
            tempPostDocumentID = tempPostDocumentId,
        )

        is UploadStateDto.SuccessPost -> UploadState.SuccessPost(
            tempPostDocumentID = tempPostDocumentId,
            postDocumentID = postDocumentId,
        )
    }
}


fun SignUpResponse.toUi(): SignUpState {
    return when (this) {
        SignUpResponse.DuplicateEmail -> SignUpState.DuplicateEmail
        SignUpResponse.Fail -> SignUpState.Fail
        is SignUpResponse.Success -> SignUpState.Success(this.userDocumentID)
    }
}

fun GalleryImage.toUi() = GalleryImageUi(
    uri = Uri.parse(uri),
    name = name,
    orderId = orderId,
)

fun GalleryImage.toPostGalleryState() = PostGalleryUiState(
    uri = Uri.parse(uri),
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
