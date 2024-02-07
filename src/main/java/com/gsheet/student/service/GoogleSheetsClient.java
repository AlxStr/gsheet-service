package com.gsheet.student.service;

import com.gsheet.student.requests.google.sheet.range.UpdateValueRangeRequest;
import com.gsheet.student.requests.google.sheet.RequestBag;
import com.gsheet.student.requests.google.sheet.range.chunk.RangeChunk;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "googleSheetsClient", url = "${google.api-uris.spreadsheets}")
@Headers({"Content-Type: application/json", "Authorization: Bearer {access_token}"})
public interface GoogleSheetsClient {

    @PostMapping("/{id}:batchUpdate")
    void request(@RequestParam("access_token") String token, @PathVariable(value = "id") String id,
            @RequestBody RequestBag request);

    @GetMapping("/{id}/values/{range}")
    RangeChunk getSheetValueRange(@RequestParam("access_token") String token, @PathVariable(value = "id") String id,
            @PathVariable(value = "range") String range);

    @PostMapping("/{id}/values/{range}:batchUpdate")
    void updateValueRange(@RequestParam("access_token") String token, @PathVariable(value = "id") String id,
            @RequestBody UpdateValueRangeRequest request);
}
