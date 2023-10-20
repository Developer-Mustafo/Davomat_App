package uz.coder.davomatapp.data

import uz.coder.davomatapp.data.student.StudentDbModel
import uz.coder.davomatapp.domain.student.Student

class MyMapper {
    fun getStudentToStudentDbModelAdd(student: Student):StudentDbModel{
        return StudentDbModel(
            name = student.name,
            surname = student.surname,
            phone = student.phone
        )
    }
    fun getStudentToStudentDbModelEdit(student: Student):StudentDbModel{
        return StudentDbModel(
            id = student.id,
            name = student.name,
            surname = student.surname,
            phone = student.phone
        )
    }
    fun getStudentDbModelToStudent(studentDbModel: StudentDbModel):Student{
        return Student(
            id = studentDbModel.id,
            name = studentDbModel.name,
            surname = studentDbModel.surname,
            phone = studentDbModel.phone
        )
    }

    fun getStudentList(list: List<StudentDbModel>) = list.map {
        getStudentDbModelToStudent(it)
    }
}