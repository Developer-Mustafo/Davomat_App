package uz.coder.davomatapp.domain.davomat

class GetDavomatByIdUseCase(private val repository: DavomatRepository) {
    suspend operator fun invoke(id:Int):Davomat{
        return repository.getDavomatById(id)
    }
}