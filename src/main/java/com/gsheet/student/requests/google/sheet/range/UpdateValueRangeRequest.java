package com.gsheet.student.requests.google.sheet.range;

import com.gsheet.student.requests.google.sheet.range.chunk.RangeChunk;
import lombok.Builder;

import java.util.List;

@lombok.Data
@Builder
public class UpdateValueRangeRequest {
    private final String valueInputOption = "RAW";

    private List<RangeChunk> data;
}
