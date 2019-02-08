package kr.co.connect.boostcamp.livewhere.firebase


interface IMapper<From, To> {

    fun map(from: From): To
}
