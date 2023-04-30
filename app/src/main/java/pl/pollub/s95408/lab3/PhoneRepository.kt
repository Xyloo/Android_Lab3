package pl.pollub.s95408.lab3

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class PhoneRepository(private val phoneDao: PhoneDao) {
    private var allElements : Flow<List<Phone>> = phoneDao.getAll()

    @WorkerThread
    suspend fun insert(phone: Phone) {
        phoneDao.insert(phone)
    }

    @WorkerThread
    suspend fun update(phone: Phone) {
        phoneDao.update(phone)
    }

    @WorkerThread
    suspend fun delete(phone: Phone) {
        phoneDao.delete(phone)
    }

    @WorkerThread
    suspend fun deleteAll() {
        phoneDao.deleteAll()
    }

    fun getAll(): Flow<List<Phone>> {
        return phoneDao.getAll()
    }
}