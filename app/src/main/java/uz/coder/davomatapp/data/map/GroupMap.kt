package uz.coder.davomatapp.data.map

import uz.coder.davomatapp.domain.model.CreateGroup
import uz.coder.davomatapp.domain.model.Group
import uz.coder.davomatapp.data.network.dto.CreateGroupRequest
import uz.coder.davomatapp.data.network.dto.GroupDTO
import javax.inject.Inject

class GroupMap @Inject constructor() {
    fun toCreateGroupRequest(createGroup: CreateGroup) = CreateGroupRequest(title = createGroup.title, courseId = createGroup.courseId)
    fun toCourse(data: GroupDTO?) = Group(courseId = data?.courseId?:0L, id = data?.id?:0L, title = data?.title?:"")

}
