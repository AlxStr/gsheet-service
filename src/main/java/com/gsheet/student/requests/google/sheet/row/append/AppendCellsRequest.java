package com.gsheet.student.requests.google.sheet.row.append;

import com.gsheet.student.requests.google.sheet.Request;
import com.gsheet.student.requests.google.sheet.row.append.chunk.RowsChunk;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AppendCellsRequest implements Request {

    private List<RowsChunk> rows;

    @Builder.Default
    private String fields = "*";

    @Override
    public String getSignature() {
        return "appendCells";
    }
}
