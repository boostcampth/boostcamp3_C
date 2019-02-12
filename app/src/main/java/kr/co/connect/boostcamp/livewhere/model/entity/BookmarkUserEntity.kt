package kr.co.connect.boostcamp.livewhere.model.entity

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class BookmarkUserEntity(
    var uuid:String? =null
){
    @Exclude
    fun toMap(): Map<String, Any> {
        val result = HashMap<String,String>()
        result.put("uuid", uuid!!)

        return result
    }
}