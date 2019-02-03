package kr.co.connect.boostcamp.livewhere.model

import com.google.gson.annotations.SerializedName

data class Bookmark(
    var imgUrl:String,
    var address:String,
    var buildingName:String,
    var isRent: Boolean,
    var pee: Int,
    var deposit: Int
)