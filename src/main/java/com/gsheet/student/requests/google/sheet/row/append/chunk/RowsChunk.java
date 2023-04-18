package com.gsheet.student.requests.google.sheet.row.append.chunk;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class RowsChunk {
    private List<ValueChunk> values;
}
