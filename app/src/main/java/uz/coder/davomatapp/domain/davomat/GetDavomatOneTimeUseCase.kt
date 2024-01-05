package uz.coder.davomatapp.domain.davomat

class GetDavomatOneTimeUseCase(private val repository: DavomatRepository) {
    suspend operator fun invoke(sId:Int,vaqt:String):Davomat{
        return repository.getDavomatOneTime(sId, vaqt)
    }
}