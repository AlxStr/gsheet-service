package com.gsheet.student.requests.google.sheet;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class RequestBag {
    private List<Map<String, Request>> requests;
}
