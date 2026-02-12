package uz.coder.davomatapp.domain.usecase.student

import uz.coder.davomatapp.domain.repository.StudentRepository
import javax.inject.Inject

class FindByGroupIdUseCase @Inject constructor(private val repository: StudentRepository) {
    operator fun invoke(groupId:Long) = repository.findByGroupId(groupId)
}