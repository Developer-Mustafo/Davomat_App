package uz.coder.davomatapp.domain.davomat

import javax.inject.Inject

class GetDavomatOneTimeUseCase @Inject constructor(private val repository: DavomatRepository) {
    suspend operator fun invoke(sId:Int,vaqt:String):Davomat{
        return repository.getDavomatOneTime(sId, vaqt)
    }
}