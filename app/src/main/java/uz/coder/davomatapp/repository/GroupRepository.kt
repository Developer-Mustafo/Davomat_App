package uz.coder.davomatapp.repository

import kotlinx.coroutines.flow.Flow
import uz.coder.davomatapp.model.CreateGroup
import uz.coder.davomatapp.model.Group

interface GroupRepository {
    fun createGroup(createGroup: CreateGroup): Flow<Group>
}