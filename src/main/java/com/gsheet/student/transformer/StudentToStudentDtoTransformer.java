package com.gsheet.student.transformer;

import com.gsheet.student.dto.StudentDto;
import com.gsheet.student.entity.Student;

final public class StudentToStudentDtoTransformer {
    public static StudentDto transform(Student student) {
        return StudentDto.builder()
            .id(student.getId())
            .firstName(student.getFirstName())
            .middleName(student.getMiddleName())
            .lastName(student.getLastName())
            .build();
    }
}
