package com.ccg.facerecognitionweb.controller;

import com.ccg.facerecognitionweb.service.Judge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.List;

@Controller
public class FileUploadController {

    @Autowired
    Judge judge;

    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public String batchUpload() {
        return "/mutifileupload";
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public @ResponseBody
    String batchUpload(HttpServletRequest request) throws Exception {
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        if (files.size()<2){
            return "you  should  upload 2 picture";
        }
        MultipartFile file = null;
        BufferedOutputStream stream = null;
        String[] filename=new String[2];
        for (int i = 0; i < files.size(); ++i) {
            file = files.get(i);
            if (!file.isEmpty()) {
                try {
                    byte[] bytes = file.getBytes();
                    filename[i]=file.getOriginalFilename();
                    stream = new BufferedOutputStream(new FileOutputStream(new File(file.getOriginalFilename())));
                    stream.write(bytes);
                    stream.close();
                } catch (Exception e) {
                    stream = null;
                    return "You failed to upload " + i + " => " + e.getMessage();
                }
            } else {
                return "You failed to upload " + i + " because the file was empty.";
            }
        }
        String s=System.getProperty("user.dir")+"\\";
        return judge.judge(s+filename[0],s+filename[1]);
    }

}
