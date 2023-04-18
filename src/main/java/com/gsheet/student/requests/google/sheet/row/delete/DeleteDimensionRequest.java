package com.gsheet.student.requests.google.sheet.row.delete;

import com.gsheet.student.requests.google.sheet.Request;
import com.gsheet.student.requests.google.sheet.row.delete.chunk.RangeChunk;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class DeleteDimensionRequest implements Request {
    private RangeChunk range;

    @Override
    public String getSignature() {
        return "deleteDimension";
    }
}