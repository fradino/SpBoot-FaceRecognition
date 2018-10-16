package com.ccg.facerecognitionweb;

import org.junit.Test;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import static com.ccg.facerecognitionweb.service.FaceRecognition.cv.CV.calcArea;

public class Glass {
    @Test
    public void dectg(){
        System.load("D:\\opencv\\build\\java\\x64\\opencv_java320.dll");
        CascadeClassifier glassDetector=new CascadeClassifier("D:\\opencv\\sources\\data\\haarcascades\\haarcascade_eye_tree_eyeglasses.xml");
        Mat image= Imgcodecs.imread("C:\\Users\\27700\\Desktop\\p1.jpg");
        MatOfRect glassDetections = new MatOfRect();
        glassDetector.detectMultiScale(image, glassDetections);
        System.out.printf("检测到%d个眼镜",glassDetections.toArray().length);
        Rect maxRect=new Rect(0,0,0,0);
        for (Rect rect : glassDetections.toArray())
        {
            if(calcArea(maxRect)<calcArea(rect))
            {
                maxRect=rect;
            }
            //给脸上面画矩形
            Imgproc.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 255, 0));
        }
        if(calcArea(maxRect)>0) {
            //创建人脸拷贝区域
            Mat roi_img = new Mat(image, maxRect);
            //创建临时的人脸拷贝图形
            Mat tmp_img = new Mat();
            //人脸拷贝
            roi_img.copyTo(tmp_img);
            // 保存最大的1张脸
            Imgcodecs.imwrite("C:\\Users\\27700\\Desktop\\p2.jpg", tmp_img);
        }
    }
}
