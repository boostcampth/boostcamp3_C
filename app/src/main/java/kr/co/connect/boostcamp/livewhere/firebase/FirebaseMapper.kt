package kr.co.connect.boostcamp.livewhere.firebase

import android.util.Log
import com.google.firebase.database.DataSnapshot
import java.lang.reflect.ParameterizedType
import java.util.*


abstract class FirebaseMapper<Entity, Model> : IMapper<Entity, Model> {

    private val entityClass: Class<Entity>
        get() {
            val superclass = javaClass.genericSuperclass as ParameterizedType
            return superclass.actualTypeArguments[0] as Class<Entity>
        }

    fun map(dataSnapshot: DataSnapshot?): Model {
        val entity = dataSnapshot!!.getValue(entityClass)
        return map(entity!!)
    }

    fun mapList(pnu:String,dataSnapshot: DataSnapshot): List<Model> {
        val list = ArrayList<Model>()
        for (item in dataSnapshot.children) {
            if(item.key == pnu) {
                item.children.forEach{
                    list.add(map(it))
                }
            }
        }
        return list
    }

}
