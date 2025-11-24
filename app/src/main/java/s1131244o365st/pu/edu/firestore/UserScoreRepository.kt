package s1131244o365st.pu.edu.firestore

import com.google.firebase.Firebase
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.tasks.await

class UserScoreRepository {
    val db = Firebase.firestore

    suspend fun addUser(userScore: UserScoreModel): String {
        return try {
            val documentReference =
                db.collection("UserScore")
                    .add(userScore)
                    .await()
            "新增資料成功！Document ID:\n ${documentReference.id}"
        } catch (e: Exception) {
            "新增資料失敗：${e.message}"
        }
    }

    suspend fun updateUser(userScore: UserScoreModel): String {
        return try {
            db.collection("UserScore")
                .document(userScore.user)
                .set(userScore)
                .await()
            "新增/異動資料成功！Document ID:\n ${userScore.user}"
        } catch (e: Exception) {
            "新增/異動資料失敗：${e.message}"
        }
    }

    suspend fun deleteUser(userScore: UserScoreModel): String {
        return try {
            val documentRef = db.collection("UserScore").document(userScore.user)
            val documentSnapshot = documentRef.get().await()

            if (documentSnapshot.exists()) {
                documentRef.delete().await()
                "刪除資料成功！Document ID: ${userScore.user}"
            } else {
                "刪除失敗：Document ID ${userScore.user} 不存在。"
            }
        } catch (e: Exception) {
            "刪除資料失敗：${e.message}"
        }
    }

    suspend fun getUserScoreByName(userScore: UserScoreModel): String {
        return try {
            val userCondition = userScore.user
            val querySnapshot = db.collection("UserScore")
                .whereEqualTo("user", userCondition)
                .get().await()

            if (!querySnapshot.isEmpty) {
                val document = querySnapshot.documents.first()
                val result = document.toObject<UserScoreModel>()
                "查詢成功！${result?.user} 的分數是 ${result?.score}"
            } else {
                "查詢失敗：找不到使用者 $userCondition 的資料。"
            }
        } catch (e: Exception) {
            "查詢資料失敗：${e.message}"
        }
    }

    suspend fun orderByScore(): String {
        return try {
            var message = ""
            val querySnapshot = db.collection("UserScore")
                .orderBy("score", Query.Direction.DESCENDING)
                .limit(3)
                .get().await()

            querySnapshot.documents.forEach { document ->
                val userScore = document.toObject<UserScoreModel>()
                userScore?.let {
                    message += "使用者 ${it.user} 的分數為 ${it.score}\n"
                }
            }

            if (message.isEmpty()) {
                "抱歉，資料庫目前無相關資料"
            } else {
                "查詢成功！分數由大到小排序為：\n$message"
            }

        } catch (e: Exception) {
            "查詢資料失敗：${e.message}"
        }
    }
}
