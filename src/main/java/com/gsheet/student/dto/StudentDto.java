package com.gsheet.student.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class StudentDto {
    private UUID id;
    private String firstName;
    private String middleName;
    private String lastName;
}
