package com.lyeye.dental_appointment.controller;

import com.lyeye.dental_appointment.entity.Photo;
import com.lyeye.dental_appointment.entity.Remote;
import com.lyeye.dental_appointment.mapper.PhotoMapper;
import com.lyeye.dental_appointment.mapper.RemoteMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressWarnings("ALL")
@RestController
public class DiagnosisController {

    @Autowired
    PhotoMapper photoMapper;

    @Autowired
    RemoteMapper remoteMapper;


    @RequestMapping(value = "/uploadImg", method = RequestMethod.POST)
    public String uploadImg(@RequestBody MultipartFile file,
                            @RequestParam(value = "userId", required = false) String userId,
                            @RequestParam(value = "userName", required = false) String userName,
                            @RequestParam(value = "hospitalName", required = false) String hospitalName) {
        String path = "E:/IDEA/WorkSpace/Lyeye/IdeaProjects/dental_appointment/photos";
        File filePath = new File(path);
        System.out.println("文件的保存路径：" + path);
        if (!filePath.exists() && !filePath.isDirectory()) {
            System.out.println("目录不存在，创建目录:" + filePath);
            filePath.mkdir();
        }

        String originalFileName = file.getOriginalFilename();
        originalFileName.replaceAll(",|&|=", "");
        System.out.println("原文件名称：" + originalFileName);
        String fileName = originalFileName.substring(0, originalFileName.lastIndexOf("."));
        fileName = fileName
                + "-"
                + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())
                + "."
                + originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
        System.out.println("新文件名称：" + fileName);
        File targetFile = new File(path, fileName);
        try {
            file.transferTo(targetFile);
            Photo photo = new Photo();
            photo.setPath(targetFile.getAbsolutePath());
            photo.setUserId(Integer.valueOf(userId));
            photo.setUserName(userName);
            photo.setUploadTo(hospitalName);
            photo.setUploadAt(new SimpleDateFormat("yyyy-MM-dd" + " " + "HH:mm:ss:SSS").format(new Date()));
            photoMapper.save(photo);
            System.out.println("上传成功");
            return "SUCCESS";
        } catch (IOException e) {
            System.out.println("上传失败：" + e);
            e.printStackTrace();
            return "FAILURE";
        }
    }

    @RequestMapping(value = "/remote", method = RequestMethod.POST)
    public boolean uploadPhoto(@RequestBody Remote remote,
                               @RequestParam(value = "userId", required = false) String userId) {
        remote.setUserId(Integer.valueOf(userId));
        return remoteMapper.insert(remote);
    }

}
