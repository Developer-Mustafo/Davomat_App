package uz.coder.davomatapp.domain.coure

class DeleteCourseUseCase(private val repository: CourseRepository) {
    suspend operator fun invoke(id:Int){
        repository.deleteCourse(id)
    }
}