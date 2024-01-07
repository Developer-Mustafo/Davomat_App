package uz.coder.davomatapp.domain.davomat

import javax.inject.Inject

class GetDavomatByIdUseCase @Inject constructor(private val repository: DavomatRepository) {
    suspend operator fun invoke(id:Int):Davomat{
        return repository.getDavomatById(id)
    }
}