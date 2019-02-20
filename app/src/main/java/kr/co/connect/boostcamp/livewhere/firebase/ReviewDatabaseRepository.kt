package kr.co.connect.boostcamp.livewhere.firebase

import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kr.co.connect.boostcamp.livewhere.model.Review
import kr.co.connect.boostcamp.livewhere.model.entity.ReviewEntity


abstract class ReviewDatabaseRepository<Entity,Model>(private val mapper:FirebaseMapper<Entity,Model>) {

    protected var databaseReference: DatabaseReference
    protected lateinit var firebaseCallback: FirebaseDatabaseRepositoryCallback<Model>
    private var listener: BaseValueEventListener<Model,Entity>? = null

    protected abstract val rootNode: String

    init {
        databaseReference = FirebaseDatabase.getInstance().getReference(rootNode)
    }

    fun postReview(reviewEntity: ReviewEntity):Task<Void>{
        val key = databaseReference.child(reviewEntity.land_code!!).child(reviewEntity.id!!).key
        val postValues = reviewEntity.toMap()
        val childUpdates = HashMap<String, Any>()
        childUpdates["/" + reviewEntity.land_code!! + "/" + key] = postValues
        return databaseReference.updateChildren(childUpdates)
    }

    fun deleteReview(review: Review):Task<Void>{
         return databaseReference.child(review.land_code!!).child(review.id!!).removeValue()
    }

    fun addListener(pnu:String, firebaseCallback: FirebaseDatabaseRepositoryCallback<Model>) {
        this.firebaseCallback = firebaseCallback
        listener = BaseValueEventListener(mapper, firebaseCallback,pnu)
        databaseReference.addValueEventListener(listener!!)
    }

    fun removeListener() {
        databaseReference.removeEventListener(listener as ValueEventListener)
    }

    interface FirebaseDatabaseRepositoryCallback<T> {
        fun onSuccess(result: List<T>)

        fun onError(e: Exception)
    }
}
