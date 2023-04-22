package pl.pollub.s95408.lab3

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "phones")
data class Phone
    (@PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Long,
     @ColumnInfo(name = "manufacturer") val manufacturer: String,
     @ColumnInfo(name = "model") val model: String,
     @ColumnInfo(name = "os_version") val os_version: String,
     @ColumnInfo(name = "website") val website: String)
{
}
