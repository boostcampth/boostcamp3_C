package kr.co.connect.boostcamp.livewhere.model

data class Review(
    var nickname: String? = null,
    var id: String? = null,
    var date:String? = null,
    var contents: String? = null,
    var land_code:String? =null
){
    constructor():this("","","","","")
}

