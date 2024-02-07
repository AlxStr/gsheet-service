package com.gsheet.student.entity;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class Student {
    private UUID id;
    private String firstName;
    private String middleName;
    private String lastName;
}
