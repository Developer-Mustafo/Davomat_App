package uz.coder.davomatapp.domain.coure

class EditCourseUseCase(private val repository: CourseRepository) {
    suspend operator fun invoke(course: Course){
        repository.editCourse(course)
    }
}