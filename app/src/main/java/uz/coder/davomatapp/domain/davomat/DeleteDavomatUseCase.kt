package uz.coder.davomatapp.domain.davomat

class DeleteDavomatUseCase(private val repository: DavomatRepository) {
    suspend operator fun invoke(id:Int){
        repository.delete(id)
    }
}