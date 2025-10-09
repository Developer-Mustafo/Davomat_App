package uz.coder.davomatapp.usecase.student

import uz.coder.davomatapp.repository.StudentRepository

class FindByGroupIdAndUserIdUseCase(private val repository: StudentRepository) {
    operator fun invoke(userId: Long, groupId:Long) = repository.findByGroupIdAndUserId(userId, groupId)
}