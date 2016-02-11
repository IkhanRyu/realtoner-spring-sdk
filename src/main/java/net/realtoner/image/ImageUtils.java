package net.realtoner.image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * provides some operations related to image.
 *
 * @author RyuIkHan
 */
public class ImageUtils {

    /**
     *
     * @param inputFile
     * @return
     * */
    private static BufferedImage getBufferedImage(File inputFile) throws IOException{
        return ImageIO.read(inputFile);
    }

    /**
     *
     * @param bufferedImage
     * @param width
     * @param height
     * @param fileType
     * @param outputFile
     * */
    private static void resize(BufferedImage bufferedImage , int width , int height , String fileType ,
                               File outputFile) throws IOException{

        Image resizedImage = bufferedImage.getScaledInstance(width, height, Image.SCALE_FAST);

        BufferedImage resizedBufferedImage = new BufferedImage(width , height , bufferedImage.getType());

        Graphics2D g=resizedBufferedImage.createGraphics();

        g.drawImage(resizedImage, 0, 0, null);

        g.dispose();

        ImageIO.write(resizedBufferedImage, fileType, new FileOutputStream(outputFile));
    }

    private static String getFileType(File file){
        return file.getName().substring(file.getName().indexOf(".")+1);
    }
    /**
     * resize given image.
     *
     * @param inputFile
     * @param outputFile
     * @param magnification
     * */
    public static void resizeToSameRatio(File inputFile , File outputFile , double magnification) throws IOException{

        BufferedImage bufferedImage = getBufferedImage(inputFile);

        int width = (int)(bufferedImage.getWidth() * magnification);
        int height = (int)(bufferedImage.getHeight() * magnification);

        String fileType = getFileType(inputFile);

        resize(bufferedImage , width , height , fileType , outputFile);
    }

    /**
     * resize given image.
     *
     * @param inputPath
     * @param outputPath
     * @param magnification
     * */
    public static void resizeToSameRatio(String inputPath , String outputPath , double magnification) throws IOException {
        resizeToSameRatio(new File(inputPath), new File(outputPath), magnification);
    }

    /**
     *
     * @param inputFile
     * @param outputFile
     * @param width
     * */
    public static void resizeToSameRatioWithFixedWidth(File inputFile , File outputFile , int width) throws IOException{

        BufferedImage bufferedImage = getBufferedImage(inputFile);

        double ratio = (double)bufferedImage.getWidth() / width;

        int height = (int)(bufferedImage.getHeight() * ratio);

        String fileType = getFileType(inputFile);

        resize(bufferedImage , width , height , fileType , outputFile);
    }

    /**
     *
     * @param inputPath
     * @param outputPath
     * @param width
     * */
    public static void resizeToSameRatioWithFixedWidth(String inputPath , String outputPath, int width) throws IOException{
        resizeToSameRatioWithFixedWidth(new File(inputPath) , new File(outputPath) ,width);
    }

    /**
     *
     * @param inputFile
     * @param outputFile
     * @param height
     * */
    public static void resizeToSameRatioWithFixedHeight(File inputFile , File outputFile , int height) throws IOException{

        BufferedImage bufferedImage = getBufferedImage(inputFile);

        double ratio = (double)bufferedImage.getHeight() / height;

        int width = (int)(bufferedImage.getWidth() * ratio);

        String fileType = getFileType(inputFile);

        resize(bufferedImage , width , height , fileType , outputFile);
    }

    /**
     *
     * @param inputPath
     * @param outputPath
     * @param height
     * */
    public static void resizeToSameRatioWithFixedHeight(String inputPath , String outputPath, int height) throws IOException{
        resizeToSameRatioWithFixedHeight(new File(inputPath) , new File(outputPath) , height);
    }
}
