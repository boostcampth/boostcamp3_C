package kr.co.connect.boostcamp.livewhere.repository
import kr.co.connect.boostcamp.livewhere.firebase.ReviewMapper
import kr.co.connect.boostcamp.livewhere.firebase.FirebaseDatabaseRepository
import kr.co.connect.boostcamp.livewhere.model.Review
import kr.co.connect.boostcamp.livewhere.model.entity.ReviewEntity
import kr.co.connect.boostcamp.livewhere.util.ROOT_NODE_NAME


class ReviewRepository : FirebaseDatabaseRepository<ReviewEntity, Review>(ReviewMapper()) {

    override val rootNode: String
        get() = ROOT_NODE_NAME
}
