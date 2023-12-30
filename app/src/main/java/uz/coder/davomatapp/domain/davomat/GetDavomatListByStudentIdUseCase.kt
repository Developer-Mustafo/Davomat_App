package uz.coder.davomatapp.domain.davomat

import androidx.lifecycle.LiveData

class GetDavomatListByStudentIdUseCase(private val repository: DavomatRepository) {
    operator fun invoke(id:Int):LiveData<List<Davomat>>{
        return repository.getDavomatListByStudentId(id)
    }
}