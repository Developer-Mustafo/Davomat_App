package uz.coder.davomatapp.domain.davomat

import androidx.lifecycle.LiveData

interface DavomatRepository {
    suspend fun add(davomat: Davomat)
    suspend fun edit(davomat: Davomat)
    suspend fun delete(id:Int)
    suspend fun getDavomatById(id: Int):Davomat
    fun getDavomatListByStudentId(id: Int):LiveData<List<Davomat>>
}