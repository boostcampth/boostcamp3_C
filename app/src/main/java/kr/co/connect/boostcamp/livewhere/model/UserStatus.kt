package kr.co.connect.boostcamp.livewhere.model

import kr.co.connect.boostcamp.livewhere.util.StatusCode

//User의 status를 캐치하는 클래스입니다.
data class UserStatus(val statusCode: StatusCode, val content: String)