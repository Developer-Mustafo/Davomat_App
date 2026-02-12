package uz.coder.davomatapp.data

import kotlinx.coroutines.flow.flow
import uz.coder.davomatapp.data.map.GroupMap
import uz.coder.davomatapp.data.network.ApiService
import uz.coder.davomatapp.domain.model.CreateGroup
import uz.coder.davomatapp.domain.repository.GroupRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GroupRepositoryImpl @Inject constructor(
    private val map: GroupMap,
    private val apiService: ApiService
): GroupRepository {
    override fun createGroup(createGroup: CreateGroup) = flow {
        val response = apiService.createGroup(map.toCreateGroupRequest(createGroup))
        if (response.code==200){
            emit(map.toCourse(response.data))
        }else if (response.code==500){
            throw RuntimeException(response.message)
        }
    }
}