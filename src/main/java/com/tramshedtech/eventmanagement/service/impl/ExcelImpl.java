package com.tramshedtech.eventmanagement.service.impl;

import com.alibaba.excel.EasyExcel;
import com.tramshedtech.eventmanagement.Listener.ExcelListener;
import com.tramshedtech.eventmanagement.entity.UserExcel;
import com.tramshedtech.eventmanagement.mapper.ExcelMapper;
import com.tramshedtech.eventmanagement.service.ExcelService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class ExcelImpl implements ExcelService {

    @Resource
    private ExcelMapper excelMapper;

    @Override
    public void saveUserList(MultipartFile file) throws IOException {
        // Process only the specified filename
        if (!file.getOriginalFilename().equals("UserTableUpdate.xls") && !file.getOriginalFilename().equals("UserTableUpdate.xlsx")) {
            return;
        }

        InputStream inputStream = new BufferedInputStream(file.getInputStream());
        try {
            // Instantiate a class that implements the AnalysisEventListener interface.
            ExcelListener excelListener = new ExcelListener(excelMapper);

            // Reading files with EasyExcel
            EasyExcel.read(inputStream, UserExcel.class, excelListener).sheet().doRead();
        } finally {
            inputStream.close();  // Ensure that the InputStream is closed correctly
        }
    }
}

