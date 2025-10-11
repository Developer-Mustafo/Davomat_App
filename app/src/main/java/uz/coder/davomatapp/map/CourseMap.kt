package uz.coder.davomatapp.map

import uz.coder.davomatapp.model.Course
import uz.coder.davomatapp.network.dto.CourseDTO

class CourseMap {
    fun toCourses(data: List<CourseDTO>?) = data?.map { Course(description = it.description?:"", id = it.id?:0L, title = it.title?:"", userId = it.userId?:0L) }?:emptyList()
}