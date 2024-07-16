package com.tramshedtech.eventmanagement.controller;


import com.tramshedtech.eventmanagement.service.FileService;
import com.tramshedtech.eventmanagement.util.MinioUtil;
import com.tramshedtech.eventmanagement.util.ResponseResult;
import com.tramshedtech.eventmanagement.util.ResponseStatus;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/files")
public class FileController {
    @Resource
    private MinioUtil minioUtil;
    @Resource
    private FileService fileService;

    @PostMapping("/upload")
    public ResponseResult upload(MultipartFile file, HttpSession httpSession){
        System.out.println("该方法被调用了");
        System.out.println(file);
        int uid = (int)httpSession.getAttribute("uid");
        MultipartFile[] multipartFiles = {file};
        List<String> urls = minioUtil.upload(multipartFiles);
        String url = urls.get(0);
        Boolean b = fileService.upload(uid,url);
        if (b){
            return new ResponseResult().setCode(200).setData(b).setStatus(ResponseStatus.SUCCESS);
        }
        return new ResponseResult().setCode(500).setStatus(ResponseStatus.FAIL);
    }

}
