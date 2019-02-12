package kr.co.connect.boostcamp.livewhere.firebase

import kr.co.connect.boostcamp.livewhere.model.Review
import kr.co.connect.boostcamp.livewhere.model.entity.ReviewEntity

class ReviewMapper : FirebaseMapper<ReviewEntity, Review>() {

    override fun map(reviewEntity: ReviewEntity): Review {
        val review = Review()
        review.id = reviewEntity.id
        review.nickname = reviewEntity.nickname
        review.contents = reviewEntity.contents
        review.land_code = reviewEntity.land_code
        return review
    }


}
