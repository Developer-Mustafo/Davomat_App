package uz.coder.davomatapp.domain.student


interface StudentRepository {
    fun add(student: Student)
    fun update(student: Student)
    fun delete(id: Int)
    fun getAllStudentList():List<Student>
    fun getByStudentId(id: Int):Student
}
