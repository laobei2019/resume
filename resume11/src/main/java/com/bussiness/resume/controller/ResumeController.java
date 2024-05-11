package com.bussiness.resume.controller;

import cn.hutool.core.io.FileUtil;
import com.base.controller.BaseController;
import com.bussiness.formwork.service.FormworkService;
import com.bussiness.resume.entity.Resume;
import com.bussiness.resume.service.ResumeService;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

@RestController
@RequestMapping("resume")
@CrossOrigin
public class ResumeController extends BaseController<ResumeService, Resume> {

    @Resource
    HttpServletRequest request;
    @Resource
    FormworkService formworkService;

    @GetMapping(value = "preview/{resumeId}/{formworkId}")
    public void showPdf(@PathVariable("resumeId") String resumeId, @PathVariable("formworkId") String formworkId, HttpServletResponse response) throws IOException {
        String file = service.preview(resumeId, formworkId, "测试用户");
        response.reset();
        response.setContentType("application/pdf");
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            OutputStream outputStream = response.getOutputStream();
            IOUtils.write(IOUtils.toByteArray(fileInputStream), outputStream);
            response.setHeader("Content-Disposition",
                    "inline; filename= file");
            outputStream.flush();
            outputStream.close();
            fileInputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping(value = "download/{resumeId}/{formworkId}/{type}")
    public void download(@PathVariable("resumeId") String resumeId, @PathVariable("formworkId") String formworkId, @PathVariable("type") String type, HttpServletResponse response) {
        try {
            final File file = new File(String.format("temp/%s/%s", resumeId, formworkId));
            formworkService.use(formworkId);
            final File[] files = file.listFiles((f) -> f.getName().endsWith(type));
            if (files.length == 1) {
                String name = files[0].getName();
                response.setCharacterEncoding("utf-8");
                response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(name, "UTF-8"));
                response.setContentType("application/octet-stream");
                final ServletOutputStream outputStream = response.getOutputStream();
                outputStream.write(FileUtil.readBytes(files[0]));
                outputStream.flush();
                outputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
