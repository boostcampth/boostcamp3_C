package kr.co.connect.boostcamp.livewhere.firebase

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


abstract class FirebaseDatabaseRepository<Entity,Model>(private val mapper:FirebaseMapper<Entity,Model>) {

    protected var databaseReference: DatabaseReference
    protected lateinit var firebaseCallback: FirebaseDatabaseRepositoryCallback<Model>
    private var listener: BaseValueEventListener<Model,Entity>? = null

    protected abstract val rootNode: String

    init {
        databaseReference = FirebaseDatabase.getInstance().getReference(rootNode)
    }

    fun postReview(any: Any){
        databaseReference.push().setValue(any)

    }

    fun addListener(pnu:String,firebaseCallback: FirebaseDatabaseRepositoryCallback<Model>) {
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
