package uz.coder.davomatapp.repositoryImpl

import kotlinx.coroutines.flow.flow
import uz.coder.davomatapp.map.GroupMap
import uz.coder.davomatapp.model.CreateGroup
import uz.coder.davomatapp.network.ApiClient
import uz.coder.davomatapp.network.ApiService
import uz.coder.davomatapp.repository.GroupRepository

class GroupRepositoryImpl: GroupRepository {
    private val map = GroupMap()
    private val apiService = ApiClient.getRetrofit().create(ApiService::class.java)
    override fun createGroup(createGroup: CreateGroup) = flow {
        val response = apiService.createGroup(map.toCreateGroupRequest(createGroup))
        if (response.code==200){
            emit(map.toCourse(response.data))
        }else if (response.code==500){
            throw RuntimeException(response.message)
        }
    }
}