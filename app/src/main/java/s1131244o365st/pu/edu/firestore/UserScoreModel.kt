package s1131244o365st.pu.edu.firestore

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class UserScoreModel(
    var user: String = "",
    var score: Int = 0,

    //讓 Firestore 在儲存文件時
    // 自動將 timestamp 欄位填入伺服器時間
    @ServerTimestamp
    var timestamp: Date? = null
)

