import androidx.lifecycle.LiveData
import com.example.myapplication.model.PokerGameDao
import com.example.myapplication.model.PokerGameSession

class PokerGameRepository(private val gameDao: PokerGameDao) {

    suspend fun insertGameSession(session: PokerGameSession) {
        gameDao.insert(session)
    }

    suspend fun deleteGameSession(sessionId: Long) {
        gameDao.deleteById(sessionId)
    }

    suspend fun updateGameSession(session: PokerGameSession) {
        gameDao.update(session)
    }

    fun getAllGameSessions(): LiveData<List<PokerGameSession>> {
        return gameDao.getAllSessions()
    }
}