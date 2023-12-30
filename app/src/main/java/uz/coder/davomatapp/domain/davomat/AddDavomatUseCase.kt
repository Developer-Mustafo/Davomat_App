package uz.coder.davomatapp.domain.davomat

class AddDavomatUseCase(private val repository: DavomatRepository) {
    suspend operator fun invoke(davomat: Davomat){
        repository.add(davomat)
    }
}