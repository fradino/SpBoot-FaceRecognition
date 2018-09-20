package com.ccg.facerecognitionweb.service;

import com.ccg.facerecognitionweb.service.FaceRecognition.cv.CV;
import com.ccg.facerecognitionweb.service.FaceRecognition.work.FaceRecognition;
import org.springframework.stereotype.Service;

@Service
public class Judge {

    FaceRecognition faceRecognition;

    public Judge() throws Exception {
         faceRecognition = new FaceRecognition();
         faceRecognition.loadModel();
    }

    public String judge(String f1, String f2) throws Exception {
        CV.cutPic(f1);
        CV.cutPic(f2);
        return faceRecognition.judge(f1, f2);
    }
}
