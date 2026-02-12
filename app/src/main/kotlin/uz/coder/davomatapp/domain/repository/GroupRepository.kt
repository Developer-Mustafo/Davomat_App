package uz.coder.davomatapp.domain.repository

import kotlinx.coroutines.flow.Flow
import uz.coder.davomatapp.domain.model.CreateGroup
import uz.coder.davomatapp.domain.model.Group

interface GroupRepository {
    fun createGroup(createGroup: CreateGroup): Flow<Group>
}