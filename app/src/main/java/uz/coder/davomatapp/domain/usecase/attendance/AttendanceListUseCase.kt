package uz.coder.davomatapp.domain.usecase.attendance

import uz.coder.davomatapp.domain.repository.AttendanceRepository
import javax.inject.Inject

class AttendanceListUseCase @Inject constructor(private val repository: AttendanceRepository) {
    operator fun invoke(studentId: Long) = repository.attendanceList(studentId)
}