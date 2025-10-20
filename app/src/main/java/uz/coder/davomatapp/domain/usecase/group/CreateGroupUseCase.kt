package uz.coder.davomatapp.domain.usecase.group

import uz.coder.davomatapp.domain.model.CreateGroup
import uz.coder.davomatapp.domain.repository.GroupRepository
import javax.inject.Inject

class CreateGroupUseCase @Inject constructor(private val response: GroupRepository) {
    operator fun invoke(createGroup: CreateGroup) = response.createGroup(createGroup)
}