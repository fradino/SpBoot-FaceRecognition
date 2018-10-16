package com.ccg.facerecognitionweb.service;

import com.ccg.facerecognitionweb.service.FaceRecognition.cv.CV;
import com.ccg.facerecognitionweb.service.FaceRecognition.work.FaceRecognition;
import org.datavec.image.loader.NativeImageLoader;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.util.ModelSerializer;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

@Service
public class Judge {

    FaceRecognition faceRecognition;
    MultiLayerNetwork restored;


    public Judge() throws Exception {
        restored = ModelSerializer.restoreMultiLayerNetwork(System.getProperty("user.dir")+"\\src\\main\\resources\\vggGlass.zip");
        faceRecognition = new FaceRecognition();
        faceRecognition.loadModel();
    }

    public String judge(String f1, String f2) throws Exception {
        CV.cutPic(f1);
        CV.cutPic(f2);
        if (glasses(f1)||glasses(f2)) {
            return ("please take off your glasses");
        }
        else {
            return faceRecognition.judge(f1, f2);
        }
    }
    public boolean glasses(String file){
        System.out.println(file);
        try {
            System.out.println(file);
            BufferedImage image1= ImageIO.read(new File(file));
            NativeImageLoader loader=new NativeImageLoader(100,100,3);
            INDArray array1=loader.asMatrix(image1);
            INDArray output = restored.output(array1);
            if (output.toIntVector()[0]==1){
                return true;
            }else {
                return false;
            }
        }catch (Exception e){
            System.out.println("no file");
        }
        return false;
    }
}
