package kr.co.connect.boostcamp.livewhere.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recent_search")
data class RecentSearchEntity(
    @PrimaryKey var text: String,
    @ColumnInfo(name = "Longitude") var longitude: String,
    @ColumnInfo(name = "Latitude") var latitude: String
)