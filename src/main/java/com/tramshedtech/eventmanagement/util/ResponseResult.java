package com.tramshedtech.eventmanagement.util;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ResponseResult {
    // Status code, which indicates the current success or failure, and can be customized by referring to the HTTP status code, for example, 500/400/401/403/415
    private int code;
    // Success message, reason for failure
    private String message;
    // Response status
    private ResponseStatus status;
    // data(Object class)
    private Object data;
}
