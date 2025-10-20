package uz.coder.davomatapp.domain.usecase.attendance

import uz.coder.davomatapp.domain.model.CreateAttendance
import uz.coder.davomatapp.domain.repository.AttendanceRepository
import javax.inject.Inject

class CreateAttendanceUseCase @Inject constructor(private val repository: AttendanceRepository) {
    operator fun invoke(createAttendance: CreateAttendance) = repository.createAttendance(createAttendance)
}