package s1131244o365st.pu.edu.firestore

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class UserScoreViewModel : ViewModel() {
    private val userScoreRepository = UserScoreRepository()

    var message by mutableStateOf("訊息")
        private set

    var user by mutableStateOf("")
        private set

    fun onUserChange(newUser: String) {
        user = newUser
    }

    fun addUser(userScore: UserScoreModel) {
        // 在 viewModelScope 中啟動一個協程
        viewModelScope.launch {
            // 呼叫 suspend function，並等待結果
            message = userScoreRepository.addUser(userScore)
        }
    }

    fun updateUser(userScore: UserScoreModel) {
        // 在 viewModelScope 中啟動一個協程
        viewModelScope.launch {
            // 呼叫 suspend function，並等待結果
            message = userScoreRepository.updateUser(userScore)
        }
    }


}
