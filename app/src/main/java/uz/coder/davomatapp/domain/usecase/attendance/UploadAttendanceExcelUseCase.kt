package uz.coder.davomatapp.domain.usecase.attendance

import uz.coder.davomatapp.domain.repository.AttendanceRepository
import java.io.File
import javax.inject.Inject

class UploadAttendanceExcelUseCase @Inject constructor(private val repository: AttendanceRepository) {
    operator fun invoke(file: File) = repository.uploadAttendanceExcel(file)
}