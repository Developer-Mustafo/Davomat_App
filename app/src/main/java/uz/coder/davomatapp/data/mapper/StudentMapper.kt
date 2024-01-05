package uz.coder.davomatapp.data.mapper

import uz.coder.davomatapp.data.student.StudentDbModel
import uz.coder.davomatapp.domain.student.Student

class StudentMapper {
    fun getStudentToStudentDbModel(student: Student):StudentDbModel{
        return StudentDbModel(
            id = student.id,
            name = student.name,
            surname = student.surname,
            phone = student.phone,
            age = student.age,
            course = student.course,
            gender = student.gender,
            courseId = student.courseId
        )
    }
    fun getStudentDbModelToStudent(studentDbModel: StudentDbModel):Student{
        return Student(
            id = studentDbModel.id,
            name = studentDbModel.name,
            surname = studentDbModel.surname,
            phone = studentDbModel.phone,
            age = studentDbModel.age,
            course = studentDbModel.course,
            gender = studentDbModel.gender,
            courseId = studentDbModel.courseId
        )
    }

    fun getStudentList(list: List<StudentDbModel>) = list.map {
        getStudentDbModelToStudent(it)
    }
}