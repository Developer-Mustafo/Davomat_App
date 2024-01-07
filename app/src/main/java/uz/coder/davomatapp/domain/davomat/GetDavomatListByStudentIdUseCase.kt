package uz.coder.davomatapp.domain.davomat

import androidx.lifecycle.LiveData
import javax.inject.Inject

class GetDavomatListByStudentIdUseCase @Inject constructor(private val repository: DavomatRepository) {
    operator fun invoke(id:Int):LiveData<List<Davomat>>{
        return repository.getDavomatListByStudentId(id)
    }
}