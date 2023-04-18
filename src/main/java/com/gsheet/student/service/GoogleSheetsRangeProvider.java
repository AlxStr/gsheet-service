package com.gsheet.student.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class GoogleSheetsRangeProvider {

    private final String from;

    private final String to;

    public String getRange() {
        return String.format("%s:%s", from, to);
    }

    public String getRange(int row) {
        return String.format("%s%s:%s%s", from, row, to, row);
    }

    public String getRange(int x, int y) {
        return String.format("%s%s:%s%s", from, x, to, y);
    }
}
