package main.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class ImageLoader {
    private HashMap<String, BufferedImage> listImage;

    public ImageLoader() {
        String path = "src/main/assets/img/";
        listImage = new HashMap<String, BufferedImage>();
        try {
            listImage.put("0", ImageIO.read(new File(path + "0.png").getAbsoluteFile()));
            listImage.put("1", ImageIO.read(new File(path + "1.png").getAbsoluteFile()));
            listImage.put("2", ImageIO.read(new File(path + "2.png").getAbsoluteFile()));
            listImage.put("3", ImageIO.read(new File(path + "3.png").getAbsoluteFile()));
            listImage.put("4", ImageIO.read(new File(path + "4.png").getAbsoluteFile()));
            listImage.put("5", ImageIO.read(new File(path + "5.png").getAbsoluteFile()));
            listImage.put("6", ImageIO.read(new File(path + "6.png").getAbsoluteFile()));
            listImage.put("7", ImageIO.read(new File(path + "7.png").getAbsoluteFile()));
            listImage.put("8", ImageIO.read(new File(path + "8.png").getAbsoluteFile()));
            listImage.put("mine", ImageIO.read(new File(path + "mine.png").getAbsoluteFile()));
            listImage.put("flag", ImageIO.read(new File(path + "flag.png").getAbsoluteFile()));
            listImage.put("exploded_mine", ImageIO.read(new File(path + "exploded_mine.png").getAbsoluteFile()));
            listImage.put("hidden", ImageIO.read(new File(path + "hidden.png").getAbsoluteFile()));
            listImage.put("timer", ImageIO.read(new File(path + "timer.png").getAbsoluteFile()));
            listImage.put("flag_icon", ImageIO.read(new File(path + "flag_icon.png").getAbsoluteFile()));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public HashMap<String, BufferedImage> getListImage() {
        return listImage;
    }
}
