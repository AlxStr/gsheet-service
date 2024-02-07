package com.gsheet.student.service;

import com.gsheet.student.dto.StudentDto;
import com.gsheet.student.entity.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentSheetService studentSheetService;

    public Map<Integer, Student> getStudents() {
        return studentSheetService.getStudents();
    }

    public Student getStudent(UUID id) {
        return studentSheetService.getStudent(id);
    }

    public Student updateStudent(UUID id, StudentDto input) {
        studentSheetService.updateStudent(id, input);

        return getStudent(id);
    }

    public Student createStudent(StudentDto input) {
        UUID id = UUID.randomUUID();
        studentSheetService.createStudent(id, input);

        return getStudent(id);
    }

    public void removeStudent(UUID id) {
        studentSheetService.removeStudent(id);
    }
}
