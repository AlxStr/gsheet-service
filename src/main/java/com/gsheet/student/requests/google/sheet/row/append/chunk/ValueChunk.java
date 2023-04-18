package com.gsheet.student.requests.google.sheet.row.append.chunk;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ValueChunk {
    private UserEnteredValue userEnteredValue;
}
