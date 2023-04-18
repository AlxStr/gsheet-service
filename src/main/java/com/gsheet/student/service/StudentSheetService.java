package com.gsheet.student.service;

import com.gsheet.student.dto.StudentDto;
import com.gsheet.student.entity.Student;
import com.gsheet.student.requests.google.sheet.RequestBag;
import com.gsheet.student.requests.google.sheet.range.UpdateValueRangeRequest;
import com.gsheet.student.requests.google.sheet.row.append.AppendCellsRequest;
import com.gsheet.student.requests.google.sheet.row.append.chunk.RowsChunk;
import com.gsheet.student.requests.google.sheet.row.append.chunk.UserEnteredValue;
import com.gsheet.student.requests.google.sheet.row.append.chunk.ValueChunk;
import com.gsheet.student.requests.google.sheet.row.delete.DeleteDimensionRequest;
import com.gsheet.student.requests.google.sheet.range.chunk.RangeChunk;
import com.gsheet.student.transformer.RawValuesToStudentTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class StudentSheetService {
    private final GoogleSheetsClient googleSheetsClient;
    @Qualifier("googleAuthTokenProvider")
    private final AuthTokenProvider googleAuthTokenProvider;

    @Qualifier("studentPersistenceRangeProvider")
    private final GoogleSheetsRangeProvider rangeProvider;

    @Value("${google.docs.spreadsheet-id}")
    private String spreadsheetId;

    public Map<Integer, Student> getStudents() {
        RangeChunk valueRange = googleSheetsClient.getSheetValueRange(googleAuthTokenProvider.getAccessToken(),
                spreadsheetId, rangeProvider.getRange());

        List<Student> students = valueRange.getValues()
            .stream()
            .filter((s -> !s.isEmpty()))
            .map(RawValuesToStudentTransformer::transform)
            .toList();

        return IntStream.range(0, valueRange.getValues()
            .size())
            .boxed()
            .collect(Collectors.toMap(i -> i + 1, students::get));
    }

    public Student getStudent(UUID id) {
        return getStudents().values()
            .stream()
            .filter((Student s) -> s.getId()
                .equals(id))
            .findFirst()
            .orElse(null);
    }

    public void updateStudent(UUID id, StudentDto input) {
        Map<Integer, Student> students = getStudents();
        Optional<Student> student = students.values()
            .stream()
            .filter(s -> s.getId()
                .equals(id))
            .findFirst();

        if (student.isEmpty()) {
            return;
        }

        Integer studentColumnId = students.entrySet()
            .stream()
            .filter(entry -> student.get()
                .equals(entry.getValue()))
            .map(Map.Entry::getKey)
            .findFirst()
            .orElse(null);

        List<List<String>> data = new ArrayList<>() {
            {
                add(Arrays.stream(
                        new String[]{id.toString(), input.getFirstName(), input.getMiddleName(), input.getLastName()})
                    .collect(Collectors.toList()));
            }
        };

        RangeChunk rangeChunk = RangeChunk.builder()
            .range(rangeProvider.getRange(studentColumnId))
            .values(data)
            .build();

        UpdateValueRangeRequest request = UpdateValueRangeRequest.builder()
            .data(List.of(rangeChunk))
            .build();

        googleSheetsClient.updateValueRange(googleAuthTokenProvider.getAccessToken(), spreadsheetId, request);
    }

    public void createStudent(UUID id, StudentDto input) {
        List<ValueChunk> valueChunks = new ArrayList<>() {
            {
                add(new ValueChunk(new UserEnteredValue(id.toString())));
                add(new ValueChunk(new UserEnteredValue(input.getFirstName())));
                add(new ValueChunk(new UserEnteredValue(input.getMiddleName())));
                add(new ValueChunk(new UserEnteredValue(input.getLastName())));
            }
        };

        List<RowsChunk> rowsChunks = new ArrayList<>();
        rowsChunks.add(new RowsChunk(valueChunks));

        AppendCellsRequest request = AppendCellsRequest.builder()
            .rows(rowsChunks)
            .build();

        RequestBag requests = RequestBag.builder()
            .requests(List.of(Map.of(request.getSignature(), request)))
            .build();

        googleSheetsClient.request(googleAuthTokenProvider.getAccessToken(), spreadsheetId, requests);
    }

    public void removeStudent(UUID id) {
        Integer rowNumber = null;
        for (var entry : getStudents().entrySet()) {
            if (entry.getValue()
                .getId()
                .equals(id)) {
                rowNumber = entry.getKey();
                break;
            }
        }

        if (Objects.isNull(rowNumber)) {
            return;
        }

        DeleteDimensionRequest request = DeleteDimensionRequest.builder()
            .range(com.gsheet.student.requests.google.sheet.row.delete.chunk.RangeChunk.builder()
                .startIndex(rowNumber - 1)
                .endIndex(rowNumber)
                .build())
            .build();

        RequestBag requests = RequestBag.builder()
            .requests(List.of(Map.of(request.getSignature(), request)))
            .build();

        googleSheetsClient.request(googleAuthTokenProvider.getAccessToken(), spreadsheetId, requests);
    }
}
