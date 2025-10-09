package uz.coder.davomatapp.usecase.attendance

import uz.coder.davomatapp.repository.AttendanceRepository

class AttendanceListUseCase(private val repository: AttendanceRepository) {
    operator fun invoke(studentId: Long) = repository.attendanceList(studentId)
}