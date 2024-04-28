package com.base.utils;

import cn.hutool.core.util.IdUtil;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.qiniu.util.Json;
import lombok.Data;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;

@Component
@Data
public class FileUploadUtil {
    private static String accessKey = "hY1ACqgJDJipZPPVi0rw3CI5DstFBVEZ099vQjj_";
    private static String secretKey = "TEOXg5oQ1PKR__dHH15kF9j4VezY2fdGAisxEfGi";
    private static String bucket = "hp-bs-img-upload";
    private static String baseUrl = "http://yjg990119.top/";

    public static String uploadFile(File file) {
        Configuration cfg = new Configuration(Region.region1());
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        UploadManager uploadManager = new UploadManager(cfg);
        try {
            Response response = uploadManager.put(file, IdUtil.fastUUID() + "_" + file.getName(), upToken);
            DefaultPutRet putRet = Json.decode(response.bodyString(), DefaultPutRet.class);
            return String.format("%s%s", baseUrl, putRet.key);
        } catch (QiniuException ex) {
            ex.printStackTrace();
            return "";
        }
    }

    public static String uploadFile(MultipartFile multipartFile) {
        File file = new File(multipartFile.getOriginalFilename());
        try {
            FileCopyUtils.copy(multipartFile.getInputStream(), Files.newOutputStream(file.toPath()));
            multipartFile.transferTo(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return uploadFile(file);
    }

    public static void download(String url, String filename) {
        OkHttpClient client = new OkHttpClient();
        Request req = new Request.Builder().url(url).build();
        okhttp3.Response resp = null;
        try {
            resp = client.newCall(req).execute();
            System.out.println(resp.isSuccessful());
            if (resp.isSuccessful()) {
                ResponseBody body = resp.body();
                InputStream is = body.byteStream();
                byte[] data = readInputStream(is);
                File file = new File(filename);
                final File parentFile = file.getParentFile();
                if (!parentFile.exists()) {
                    parentFile.mkdir();
                }
                FileOutputStream fops = new FileOutputStream(file);
                fops.write(data);
                fops.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static byte[] readInputStream(InputStream is) {
        ByteArrayOutputStream writer = new ByteArrayOutputStream();
        byte[] buff = new byte[1024 * 2];
        int len = 0;
        try {
            while ((len = is.read(buff)) != -1) {
                writer.write(buff, 0, len);
            }
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return writer.toByteArray();
    }
}
