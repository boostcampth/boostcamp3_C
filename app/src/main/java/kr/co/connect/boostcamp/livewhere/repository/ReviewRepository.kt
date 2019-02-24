package kr.co.connect.boostcamp.livewhere.repository

import kr.co.connect.boostcamp.livewhere.firebase.ReviewDatabaseRepository
import kr.co.connect.boostcamp.livewhere.firebase.ReviewMapper
import kr.co.connect.boostcamp.livewhere.model.Review
import kr.co.connect.boostcamp.livewhere.model.entity.ReviewEntity
import kr.co.connect.boostcamp.livewhere.util.ROOT_COMMENTS_NAME


class ReviewRepository : ReviewDatabaseRepository<ReviewEntity, Review>(ReviewMapper()) {

    override val rootNode: String
        get() = ROOT_COMMENTS_NAME
}
