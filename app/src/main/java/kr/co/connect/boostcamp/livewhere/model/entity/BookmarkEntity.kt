package kr.co.connect.boostcamp.livewhere.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmark")
data class BookmarkEntity(
    @PrimaryKey var address: String,
    @ColumnInfo(name = "building_name") var building_name: String,
    @ColumnInfo(name = "image_url") var img_url: String
)