package uz.coder.davomatapp.data.mapper

import uz.coder.davomatapp.data.course.CourseDbModel
import uz.coder.davomatapp.data.student.StudentDbModel
import uz.coder.davomatapp.domain.coure.Course
import uz.coder.davomatapp.domain.student.Student

class CourseMapper {
    fun getCourseToCourseDbModel(course: Course): CourseDbModel {
        return CourseDbModel(
            id = course.id,
            name = course.name
        )
    }
    fun getCourseDbModelToCourse(courseDbModel: CourseDbModel): Course {
        return Course(
            id = courseDbModel.id,
            name = courseDbModel.name
        )
    }

    fun getCourseList(list: List<CourseDbModel>) = list.map {
        getCourseDbModelToCourse(it)
    }
}