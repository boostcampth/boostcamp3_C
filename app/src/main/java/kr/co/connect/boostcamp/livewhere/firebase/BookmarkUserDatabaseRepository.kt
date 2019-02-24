package kr.co.connect.boostcamp.livewhere.firebase

import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kr.co.connect.boostcamp.livewhere.model.entity.BookmarkUserEntity

abstract class BookmarkUserDatabaseRepository<Entity, Model>(private val mapper: FirebaseMapper<Entity, Model>) {

    protected var databaseReference: DatabaseReference
    protected lateinit var firebaseCallback: FirebaseDatabaseRepositoryCallback<Model>
    private var listener: BookmarkUserEventListener<Model, Entity>? = null

    protected abstract val rootNode: String

    init {
        databaseReference = FirebaseDatabase.getInstance().getReference(rootNode)
    }

    fun addBookmark(pnu: String, bookmarkUserEntity: BookmarkUserEntity): Task<Void> {
        val postValues = bookmarkUserEntity.toMap()
        val childUpdates = HashMap<String, Any>()
        childUpdates["/" + pnu + "/" + bookmarkUserEntity.uuid] = postValues
        return databaseReference.updateChildren(childUpdates)
    }

    fun deleteBookmark(pnu: String, uuid: String): Task<Void> {
        return databaseReference.child(pnu).child(uuid).removeValue()
    }

    fun addListener(pnu: String, firebaseCallback: FirebaseDatabaseRepositoryCallback<Model>) {
        this.firebaseCallback = firebaseCallback
        listener = BookmarkUserEventListener(mapper, firebaseCallback, pnu)
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