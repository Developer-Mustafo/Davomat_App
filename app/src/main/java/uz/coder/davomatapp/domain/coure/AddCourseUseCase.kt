package uz.coder.davomatapp.domain.coure

class AddCourseUseCase(private val repository: CourseRepository) {
    suspend operator fun invoke(course: Course){
        repository.addCourse(course)
    }
}