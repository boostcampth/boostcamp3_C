package kr.co.connect.boostcamp.livewhere.firebase

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class BookmarkUserEventListener<Model, Entity>(
    private val mapper: FirebaseMapper<Entity, Model>,
    private val callback: BookmarkUserDatabaseRepository.FirebaseDatabaseRepositoryCallback<Model>,
    private val pnu: String
) : ValueEventListener {

    override fun onDataChange(dataSnapshot: DataSnapshot) {
        val data = mapper.mapList(pnu, dataSnapshot)
        callback.onSuccess(data)
    }

    override fun onCancelled(databaseError: DatabaseError) {
        callback.onError(databaseError.toException())
    }
}
