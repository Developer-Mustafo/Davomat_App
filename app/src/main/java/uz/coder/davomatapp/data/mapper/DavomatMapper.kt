package uz.coder.davomatapp.data.mapper

import uz.coder.davomatapp.data.davomat.DavomatDbModel
import uz.coder.davomatapp.domain.davomat.Davomat

class DavomatMapper {
    fun getDavomatToDavomatDbModel(davomat: Davomat):DavomatDbModel{
        return DavomatDbModel(
            id = davomat.id,
            name = davomat.name,
            surname = davomat.surname,
            davomat = davomat.davomat,
            studentId = davomat.studentId,
            vaqt = davomat.vaqt,
            gender = davomat.gender
        )
    }
    fun getDavomatDbModelToDavomat(davomatDbModel: DavomatDbModel):Davomat{
        return Davomat(
            id = davomatDbModel.id,
            name = davomatDbModel.name,
            surname = davomatDbModel.surname,
            davomat = davomatDbModel.davomat,
            studentId = davomatDbModel.studentId,
            vaqt = davomatDbModel.vaqt,
            gender = davomatDbModel.gender
        )
    }

    fun getDavomatList(list: List<DavomatDbModel>) = list.map {
        getDavomatDbModelToDavomat(it)
    }
}
