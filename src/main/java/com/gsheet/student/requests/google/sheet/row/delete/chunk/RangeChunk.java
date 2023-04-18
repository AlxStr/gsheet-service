package com.gsheet.student.requests.google.sheet.row.delete.chunk;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RangeChunk {

    @Builder.Default
    private String dimension = "ROWS";

    private Integer startIndex;

    private Integer endIndex;
}
