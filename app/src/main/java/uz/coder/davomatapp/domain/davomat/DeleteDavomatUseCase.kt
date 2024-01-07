package uz.coder.davomatapp.domain.davomat

import javax.inject.Inject

class DeleteDavomatUseCase @Inject constructor(private val repository: DavomatRepository) {
    suspend operator fun invoke(id:Int){
        repository.delete(id)
    }
}