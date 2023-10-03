package uz.coder.davomatapp.domain.student


interface StudentRepository {
    fun add(student: Student)
    fun update(student: Student)
    fun delete(student: Student)
    fun getAllStudentList():List<Student>
    fun getByStudentId(id:Int): Student
}
