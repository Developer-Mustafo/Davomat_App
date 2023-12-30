package uz.coder.davomatapp.domain.davomat

class EditDavomatUseCase(private val repository: DavomatRepository) {
    suspend operator fun invoke(davomat: Davomat){
        repository.edit(davomat)
    }
}