package com.tramshedtech.eventmanagement.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ExcelService {
    void saveUserList(MultipartFile file) throws IOException;
}
