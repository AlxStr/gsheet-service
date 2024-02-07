package com.gsheet.student.transformer;

import com.gsheet.student.entity.Student;

import java.util.List;
import java.util.UUID;

public class RawValuesToStudentTransformer {
    public static Student transform(List<String> values) {
        UUID id = null;
        String firstName = null;
        String middleName = null;
        String lastName = null;

        if (1 <= values.toArray().length) {
            id = UUID.fromString(values.get(0));
        }

        if (2 <= values.toArray().length) {
            firstName = values.get(1);
        }

        if (3 <= values.toArray().length) {
            middleName = values.get(2);
        }

        if (4 <= values.toArray().length) {
            lastName = values.get(3);
        }

        return Student.builder()
            .id(id)
            .firstName(firstName)
            .middleName(middleName)
            .lastName(lastName)
            .build();
    }
}
