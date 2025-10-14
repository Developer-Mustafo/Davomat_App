package uz.coder.davomatapp.map

import uz.coder.davomatapp.model.Course
import uz.coder.davomatapp.model.CreateCourse
import uz.coder.davomatapp.network.dto.CourseDTO
import uz.coder.davomatapp.network.dto.CreateCourseRequest

class CourseMap {
    fun toCourses(data: List<CourseDTO>?) = data?.map { Course(description = it.description?:"", id = it.id?:0L, title = it.title?:"", userId = it.userId?:0L) }?:emptyList()
    fun toCourseRequest(createCourse: CreateCourse) = CreateCourseRequest(title = createCourse.title, description = createCourse.description, userId = createCourse.userId)
    fun toCourse(data: CourseDTO?) = Course(description = data?.description?:"", id = data?.id?:0L, title = data?.title?:"", userId = data?.userId?:0L)
}