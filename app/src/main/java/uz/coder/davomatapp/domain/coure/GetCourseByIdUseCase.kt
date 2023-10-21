package uz.coder.davomatapp.domain.coure

class GetCourseByIdUseCase(private val repository: CourseRepository) {
    suspend operator fun invoke(id:Int):Course{
        return repository.getByIdCourse(id)
    }
}