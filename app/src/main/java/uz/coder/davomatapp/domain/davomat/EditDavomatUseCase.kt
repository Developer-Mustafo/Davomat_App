package uz.coder.davomatapp.domain.davomat

import javax.inject.Inject

class EditDavomatUseCase @Inject constructor(private val repository: DavomatRepository) {
    suspend operator fun invoke(davomat: Davomat){
        repository.edit(davomat)
    }
}