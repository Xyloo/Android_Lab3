package pl.pollub.s95408.lab3

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PhoneDao
{
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(phone: Phone)

    @Update
    suspend fun update(phone: Phone)

    @Delete
    suspend fun delete(phone: Phone)

    @Query("DELETE FROM phones")
    suspend fun deleteAll()

    @Query("SELECT * FROM phones ORDER BY manufacturer ASC")
    fun getAll(): Flow<List<Phone>>
}