package kr.co.connect.boostcamp.livewhere.model.entity

import com.google.firebase.database.IgnoreExtraProperties


@IgnoreExtraProperties
data class ReviewEntity(
    var nickname: String? = null,
    var id: String? = null,
    var contents: String? = null,
    var land_code:String? =null
)

