package com.gsheet.student.controller;

import com.gsheet.student.dto.StudentDto;
import com.gsheet.student.entity.Student;
import com.gsheet.student.service.StudentService;
import com.gsheet.student.transformer.StudentToStudentDtoTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @GetMapping
    public List<StudentDto> all() {
        return studentService.getStudents()
            .values()
            .stream()
            .map(StudentToStudentDtoTransformer::transform)
            .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDto> get(@PathVariable UUID id, @RequestParam(defaultValue = "") String param) {
        if (param.equals("exception")) {
            throw new RuntimeException("Exception for testing");
        } else if (param.equals("404")) {
            return ResponseEntity.notFound()
                .build();
        }

        Optional<Student> student = Optional.ofNullable(studentService.getStudent(id));

        return ResponseEntity.ok(student.map(StudentToStudentDtoTransformer::transform)
            .orElse(null));
    }

    @PostMapping
    public StudentDto create(@RequestBody StudentDto input) {
        Optional<Student> student = Optional.ofNullable(studentService.createStudent(input));

        return student.map(StudentToStudentDtoTransformer::transform)
            .orElse(null);
    }

    @PutMapping("/{id}")
    public StudentDto update(@PathVariable UUID id, @RequestBody StudentDto input) {
        Student student = studentService.updateStudent(id, input);

        return StudentToStudentDtoTransformer.transform(student);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        studentService.removeStudent(id);
    }
}
