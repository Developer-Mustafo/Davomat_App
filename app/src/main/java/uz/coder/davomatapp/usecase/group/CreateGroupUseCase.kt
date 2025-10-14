package uz.coder.davomatapp.usecase.group

import uz.coder.davomatapp.model.CreateGroup
import uz.coder.davomatapp.repository.GroupRepository

class CreateGroupUseCase(private val response: GroupRepository) {
    operator fun invoke(createGroup: CreateGroup) = response.createGroup(createGroup)
}