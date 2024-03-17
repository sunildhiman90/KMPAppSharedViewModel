import dev.icerock.moko.mvvm.flow.CMutableStateFlow
import dev.icerock.moko.mvvm.flow.cFlow
import dev.icerock.moko.mvvm.flow.cMutableStateFlow
import dev.icerock.moko.mvvm.flow.cStateFlow
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    //Declare it MutableStateFlow for using with binding form ios
    val username: CMutableStateFlow<String> = MutableStateFlow("").cMutableStateFlow()
    val password: CMutableStateFlow<String> = MutableStateFlow("").cMutableStateFlow()

    private val _isProcessing: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isProcessing = _isProcessing.asStateFlow().cStateFlow()

    val isButtonEnabled: StateFlow<Boolean> = combine(username, password) { username, password ->
        username.isNotBlank() && password.isNotBlank()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false).cStateFlow()

    private val _actions = Channel<Action>()
    val actions = _actions.receiveAsFlow().cFlow()

    fun onButtonPressed() {
        _isProcessing.value = true
        viewModelScope.launch {
            delay(1000)//simulate api
            _isProcessing.value = false
            _actions.send(Action.LoginSuccess)
        }
    }

    sealed class Action {
        data object LoginSuccess : Action()
    }

}