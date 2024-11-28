package com.bestapp.zipbab.ui.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bestapp.zipbab.domain.model.TermsAndCondition
import com.bestapp.zipbab.domain.model.user.UserPrivate
import com.bestapp.zipbab.domain.repository.AppSettingRepository
import com.bestapp.zipbab.domain.repository.AuthRepository
import com.bestapp.zipbab.domain.util.onError
import com.bestapp.zipbab.domain.util.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val appSettingRepository: AppSettingRepository,
    private val authRepository: AuthRepository,
) : ViewModel() {

    val userPrivate: StateFlow<UserPrivate> = appSettingRepository.userPrivateData
        .map { data ->
            data ?: UserPrivate()
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = UserPrivate(),
        )

    private val _navActionIntent = MutableStateFlow<NavActionIntent>(NavActionIntent.Default)
    val navActionIntent: StateFlow<NavActionIntent> = _navActionIntent.asStateFlow()

    private val _actionIntent = MutableStateFlow<ActionIntent>(ActionIntent.Default)
    val actionIntent: StateFlow<ActionIntent> = _actionIntent.asStateFlow()

    private val _message = MutableSharedFlow<SettingMessage>()
    val message: SharedFlow<SettingMessage> = _message.asSharedFlow()

    private val _requestDeleteUrl = MutableStateFlow("")
    val requestDeleteUrl: StateFlow<String> = _requestDeleteUrl.asStateFlow()

    private val _requestPrivacyUrl = MutableStateFlow(TermsAndCondition())
    val requestPrivacyUrl: StateFlow<TermsAndCondition> = _requestPrivacyUrl.asStateFlow()

    private val _requestLocationPolicyUrl = MutableStateFlow(TermsAndCondition())
    val requestLocationPolicyUrl: StateFlow<TermsAndCondition> =
        _requestLocationPolicyUrl.asStateFlow()

    private val _userInfoLoadState = MutableStateFlow<LoadingState>(LoadingState.Default)
    val userInfoLodeState: StateFlow<LoadingState> = _userInfoLoadState.asStateFlow()

    init {
        viewModelScope.launch {
            appSettingRepository.getDeleteRequestUrl().onSuccess {
                _requestDeleteUrl.value = it
            }
            appSettingRepository.getPrivacyPolicy().onSuccess {
                _requestPrivacyUrl.value = it
            }
            appSettingRepository.getLocationPolicy().onSuccess {
                _requestLocationPolicyUrl.value = it
            }
        }
    }

    fun handleAction(settingIntent: SettingIntent) {
        when (settingIntent) {
            SettingIntent.Default -> {
                _navActionIntent.value = NavActionIntent.Default
            }

            SettingIntent.SignOut -> signOut()
            SettingIntent.SignOutTry -> {
                _actionIntent.value = ActionIntent.SignOutTry
            }

            SettingIntent.Logout -> logout()
            SettingIntent.Login -> {
                _navActionIntent.value = NavActionIntent.Login
            }

            SettingIntent.Profile -> {
                _navActionIntent.value = NavActionIntent.Profile(userPrivate.value.id)
            }

            SettingIntent.Meeting -> {
                _navActionIntent.value = NavActionIntent.Meeting
            }

            SettingIntent.SignUp -> {
                _navActionIntent.value = NavActionIntent.SignUp
            }

            SettingIntent.RequestDelete -> {
                _actionIntent.value = ActionIntent.DirectToRequestDelete(
                    url = requestDeleteUrl.value
                )
            }

            SettingIntent.NotYetImplemented -> {
                _actionIntent.value = ActionIntent.NotYetImplemented
            }

            SettingIntent.PrivacyPolicy -> {
                _actionIntent.value = ActionIntent.PrivacyPolicy
            }

            SettingIntent.LocationPolicy -> {
                _actionIntent.value = ActionIntent.LocationPolicy
            }
        }
    }

    private fun logout() {
        viewModelScope.launch(Dispatchers.IO) {
            appSettingRepository.removePrivateData()
            _message.emit(SettingMessage.LogoutSuccess)
        }
    }

    private fun signOut() {
        viewModelScope.launch {
            runCatching {
                authRepository.signOutUser(userPrivate.value.id).onSuccess { isSignOutDone ->
                    if (isSignOutDone) {
                        _message.emit(SettingMessage.SingOutSuccess)
                    } else {
                        _message.emit(SettingMessage.SignOutFail)
                    }
                }.onError {
                    // TODO : NetworkError 헨들링 처리 확장함수 만들면서 처리
                    _message.emit(SettingMessage.SignOutFail)
                }
            }
        }
    }

    fun onActionIntentConsumed() {
        _actionIntent.value = ActionIntent.Default
    }

    fun onNavActionIntentConsumed() {
        _navActionIntent.value = NavActionIntent.Default
    }
}
