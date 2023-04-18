package com.gsheet.student.requests.google.sheet.range.chunk;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RangeChunk {
    private String range;

    private List<List<String>> values;
}
