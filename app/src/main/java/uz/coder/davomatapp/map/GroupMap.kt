package uz.coder.davomatapp.map

import uz.coder.davomatapp.model.CreateGroup
import uz.coder.davomatapp.model.Group
import uz.coder.davomatapp.network.dto.CreateGroupRequest
import uz.coder.davomatapp.network.dto.GroupDTO

class GroupMap {
    fun toCreateGroupRequest(createGroup: CreateGroup) = CreateGroupRequest(title = createGroup.title, courseId = createGroup.courseId)
    fun toCourse(data: GroupDTO?) = Group(courseId = data?.courseId?:0L, id = data?.id?:0L, title = data?.title?:"")

}
