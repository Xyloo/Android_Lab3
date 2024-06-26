package pl.pollub.s95408.lab3

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class PhoneApplication: Application() {
    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { PhoneRoomDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { PhoneRepository(database.phoneDao()) }
}