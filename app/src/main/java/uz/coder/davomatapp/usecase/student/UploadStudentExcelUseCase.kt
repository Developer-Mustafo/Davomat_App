package uz.coder.davomatapp.usecase.student

import uz.coder.davomatapp.repository.StudentRepository
import java.io.File

class UploadStudentExcelUseCase(private val repository: StudentRepository) {
    operator fun invoke(file: File, userId:Long) = repository.uploadStudentExcel(file, userId)
}