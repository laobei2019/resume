package com.base.controller;

import com.base.common.ResponseData;
import com.base.utils.FileUploadUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("file")
@CrossOrigin
public class FileController {

    /*文件上传*/
    @PostMapping
    @RequestMapping("upload")
    public ResponseData upload(@RequestParam("file") MultipartFile file) {
        String url = FileUploadUtil.uploadFile(file);
        return ResponseData.success("").put("url",url).put("file",file.getOriginalFilename());
    }
}