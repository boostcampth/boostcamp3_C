package kr.co.connect.boostcamp.livewhere.model

data class PlaceRequest(val name: String)

enum class Category(val type: Int) {
    HOUSE(1001), PLACE(1002), MARKET(1003), RESTARUANT(1004),
    HOSPITAL(1005), CAFE(1006), SCHOOL(1007), EMPTY(1008);
}