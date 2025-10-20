package uz.coder.davomatapp.domain.usecase.student

import uz.coder.davomatapp.domain.repository.StudentRepository
import javax.inject.Inject

class FindByGroupIdAndUserIdUseCase @Inject constructor(private val repository: StudentRepository) {
    operator fun invoke(userId: Long, groupId:Long) = repository.findByGroupIdAndUserId(userId, groupId)
}