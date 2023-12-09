package uz.coder.davomatapp.data.mapper

import uz.coder.davomatapp.data.course.CourseDbModel
import uz.coder.davomatapp.domain.coure.Course

class CourseMapper {
    fun getCourseToCourseDbModel(course: Course): CourseDbModel {
        return CourseDbModel(
            id = course.id,
            name = course.name,
            adminId = course.adminId
        )
    }
    fun getCourseDbModelToCourse(courseDbModel: CourseDbModel): Course {
        return Course(
            id = courseDbModel.id,
            name = courseDbModel.name,
            adminId = courseDbModel.adminId
        )
    }

    fun getCourseList(list: List<CourseDbModel>) = list.map {
        getCourseDbModelToCourse(it)
    }
}