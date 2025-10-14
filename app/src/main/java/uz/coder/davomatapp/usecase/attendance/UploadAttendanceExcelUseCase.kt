package uz.coder.davomatapp.usecase.attendance

import uz.coder.davomatapp.repository.AttendanceRepository
import java.io.File

class UploadAttendanceExcelUseCase(private val repository: AttendanceRepository) {
    operator fun invoke(file: File) = repository.uploadAttendanceExcel(file)
}