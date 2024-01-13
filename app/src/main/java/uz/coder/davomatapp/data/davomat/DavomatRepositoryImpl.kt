package uz.coder.davomatapp.data.davomat

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import uz.coder.davomatapp.data.db.MyDatabase
import uz.coder.davomatapp.data.mapper.DavomatMapper
import uz.coder.davomatapp.domain.davomat.Davomat
import uz.coder.davomatapp.domain.davomat.DavomatRepository
import javax.inject.Inject

class DavomatRepositoryImpl @Inject constructor(
    private val db:DavomatDao,
    private val mapper:DavomatMapper
):DavomatRepository {
    override suspend fun add(davomat: Davomat) {
        db.add(mapper.getDavomatToDavomatDbModel(davomat))
    }

    override suspend fun edit(davomat: Davomat) {
        db.add(mapper.getDavomatToDavomatDbModel(davomat))
    }

    override suspend fun delete(id: Int) {
        db.delete(id)
    }

    override suspend fun getDavomatById(id: Int): Davomat {
        return mapper.getDavomatDbModelToDavomat(db.getDavomatById(id))
    }

    override fun getDavomatListByStudentId(id: Int): LiveData<List<Davomat>> =
        MediatorLiveData<List<Davomat>>().apply {
            addSource(db.getDavomatListByStudentId(id)) {
                value = mapper.getDavomatList(it)
            }
        }

    override suspend fun getDavomatOneTime(sId: Int, vaqt: String): Davomat {
        return try {
            mapper.getDavomatDbModelToDavomat(db.getDavomatOneTime(sId, vaqt))
        }catch (e:Exception){
            Davomat(name = "aa", surname = "aa", gender = "aa", davomat = "aa", vaqt = "aa")
        }
    }
}