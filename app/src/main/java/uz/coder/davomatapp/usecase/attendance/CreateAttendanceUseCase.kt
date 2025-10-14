package uz.coder.davomatapp.usecase.attendance

import uz.coder.davomatapp.model.CreateAttendance
import uz.coder.davomatapp.repository.AttendanceRepository

class CreateAttendanceUseCase(private val repository: AttendanceRepository) {
    operator fun invoke(createAttendance: CreateAttendance) = repository.createAttendance(createAttendance)
}