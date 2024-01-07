package uz.coder.davomatapp.domain.davomat

import javax.inject.Inject

class AddDavomatUseCase @Inject constructor(private val repository: DavomatRepository) {
    suspend operator fun invoke(davomat: Davomat){
        repository.add(davomat)
    }
}