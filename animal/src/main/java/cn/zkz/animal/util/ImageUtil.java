package cn.zkz.animal.util;
import javafx.scene.shape.Circle;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;



public class ImageUtil {

    public static void main(String[] args) throws IOException {
        test_cut_image();
    }

    public static void test_cut_image() throws IOException {
        String imgPath = "E:\\animal\\img\\namelist-chara396.png";
        String subPath = "E:\\animal\\img\\ttttt_namelist-chara396.png";

        File f = new File(imgPath);
        File t = new File(subPath);
        if (t.exists()) {
            t.delete();
        }

        //图片输入流
        ImageInputStream iis = ImageIO.createImageInputStream(f);

        //图片读取器
        Iterator<ImageReader> it = ImageIO.getImageReaders(iis);

        if (it.hasNext()) {
            ImageReader r = it.next();

            //设置输入流
            r.setInput(iis, true);
            System.out.println("格式=" + r.getFormatName());
            System.out.println("宽=" + r.getWidth(0));
            System.out.println("高=" + r.getHeight(0));

            //读取参数
            ImageReadParam param = r.getDefaultReadParam();

            //创建要截取的矩形范围
            Rectangle rect = new Rectangle(150, 0, 284, 148);
            Circle c = new Circle();

            //设置截取范围参数
            param.setSourceRegion(rect);

            //读取截图数据
            BufferedImage bi = r.read(0, param);

            // 保存图片
            ImageIO.write(bi, "png", t);
        }
    }
}