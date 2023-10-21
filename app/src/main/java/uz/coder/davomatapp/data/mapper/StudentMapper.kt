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
            img = student.img,
            course = student.course
        )
    }
    fun getStudentDbModelToStudent(studentDbModel: StudentDbModel):Student{
        return Student(
            id = studentDbModel.id,
            name = studentDbModel.name,
            surname = studentDbModel.surname,
            phone = studentDbModel.phone,
            age = studentDbModel.age,
            img = studentDbModel.img,
            course = studentDbModel.course
        )
    }

    fun getStudentList(list: List<StudentDbModel>) = list.map {
        getStudentDbModelToStudent(it)
    }
}