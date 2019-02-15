package kr.co.connect.boostcamp.livewhere.model.entity

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties


@IgnoreExtraProperties
data class ReviewEntity(
    var nickname: String? = null,
    var id: String? = null,
    var date:String? = null,
    var contents: String? = null,
    var land_code:String? =null
){
    @Exclude
    fun toMap(): Map<String, Any> {
        val result = HashMap<String,String>()
        result.put("id", id!!)
        result.put("nickname", nickname!!)
        result.put("date",date!!)
        result.put("contents", contents!!)
        result.put("land_code", land_code!!)

        return result
    }
}

