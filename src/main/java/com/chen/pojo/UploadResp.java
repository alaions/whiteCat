package com.chen.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UploadResp {
    private String code;
    private String message;
    private String fileName;

    public UploadResp(String code, String message) {
        new UploadResp(code, message, "");
    }

    public UploadResp(String code, String message, String fileName) {
        this.code = code;
        this.message = message;
        this.fileName = fileName;
    }
}
