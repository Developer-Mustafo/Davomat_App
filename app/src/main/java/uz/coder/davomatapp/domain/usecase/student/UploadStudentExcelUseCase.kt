package uz.coder.davomatapp.domain.usecase.student

import uz.coder.davomatapp.domain.repository.StudentRepository
import java.io.File
import javax.inject.Inject

class UploadStudentExcelUseCase @Inject constructor(private val repository: StudentRepository) {
    operator fun invoke(file: File, userId:Long) = repository.uploadStudentExcel(file, userId)
}