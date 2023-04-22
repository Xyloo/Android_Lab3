package pl.pollub.s95408.lab3

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = arrayOf(Phone::class), version = 1, exportSchema = false)
abstract class PhoneRoomDatabase : RoomDatabase()
{
    abstract fun phoneDao(): PhoneDao

    private class PhoneDatabaseCallback(private val scope: CoroutineScope) : RoomDatabase.Callback()
    {
        override fun onCreate(db: SupportSQLiteDatabase)
        {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    val phoneDao = database.phoneDao()
                    phoneDao.deleteAll()
                    var phone = Phone(0, "Samsung", "Galaxy S10", "Android 9", "https://www.samsung.com/global/galaxy/galaxy-s10/specs/")
                    phoneDao.insert(phone)
                    phone = Phone(0, "Apple", "iPhone 11", "iOS 13", "https://www.apple.com/iphone-11/specs/")
                    phoneDao.insert(phone)
                    phone = Phone(0, "Xiaomi", "Mi 9", "Android 9", "https://www.mi.com/global/mi-9/specs/")
                    phoneDao.insert(phone)
                }
            }
        }
    }

    companion object
    {
        @Volatile
        private var INSTANCE: PhoneRoomDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): PhoneRoomDatabase
        {
            return INSTANCE ?: synchronized(this)
            {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PhoneRoomDatabase::class.java,
                    "phones_database"
                ).addCallback(PhoneDatabaseCallback(scope)).fallbackToDestructiveMigration().build()
                this.INSTANCE = instance
                instance
            }
        }
    }
}