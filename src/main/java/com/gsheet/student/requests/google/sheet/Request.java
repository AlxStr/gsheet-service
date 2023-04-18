package com.gsheet.student.requests.google.sheet;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface Request {

    @JsonIgnore
    String getSignature();
}
