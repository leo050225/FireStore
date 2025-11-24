package s1131244o365st.pu.edu.firestore

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import s1131244o365st.pu.edu.firestore.ui.theme.FireStoreTheme


class MainActivity : ComponentActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val db = Firebase.firestore

        // 建立一個包含姓與名的新使用者
        val user = hashMapOf(
            "first" to "Ada",
            "last" to "Lovelace",
            "born" to 1815
        )

        // 新增一個具有自動產生 ID 的文件
        db.collection("users")
            .add(user)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }

        setContent {
            FireStoreTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
                UserScoreScreen()
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FireStoreTheme {
        Greeting("Android")
    }
}

@Composable
fun UserScoreScreen( userScoreViewModel: UserScoreViewModel = viewModel()
) {
    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            // 在按鈕點擊時，直接呼叫 ViewModel 的函式
            var userScore = UserScoreModel("子青", 39)
            userScoreViewModel.addUser(userScore)
        }) {
            Text("新增資料")
        }
        Button(onClick = {
            // 在按鈕點擊時，直接呼叫 ViewModel 的函式
            var userScore = UserScoreModel("施聿觀", 666)
            userScoreViewModel.updateUser(userScore)
        }) {
            Text("新增/異動資料")
        }

        Text(userScoreViewModel.message)
    }
}

