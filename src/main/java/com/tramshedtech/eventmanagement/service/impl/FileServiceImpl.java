package com.tramshedtech.eventmanagement.service.impl;

import com.tramshedtech.eventmanagement.mapper.FileMapper;
import com.tramshedtech.eventmanagement.service.FileService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;


@Service
public class FileServiceImpl implements FileService {

    @Resource
    private FileMapper fileMapper;
    @Override
    public Boolean upload(int uid, String url) {
        return fileMapper.upload(uid,url);
    }
}
