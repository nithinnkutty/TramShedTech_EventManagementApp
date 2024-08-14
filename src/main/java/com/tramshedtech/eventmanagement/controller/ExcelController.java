package com.tramshedtech.eventmanagement.controller;

import com.tramshedtech.eventmanagement.service.ExcelService;
import com.tramshedtech.eventmanagement.util.ResponseResult;
import com.tramshedtech.eventmanagement.util.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.Resource;
import java.io.IOException;

@RestController
@ResponseBody
@RequestMapping("/excel")
public class ExcelController {

    @Resource
    private ExcelService excelService;

    @PostMapping("/upload")
    public ResponseResult uploadEasyExcl(@RequestParam("file") MultipartFile file) throws IOException {
        excelService.saveUserList(file);
        ResponseResult responseResult = new ResponseResult();
        responseResult.setCode(200);
        responseResult.setStatus(ResponseStatus.SUCCESS);
        return responseResult;
    }
}
