package main.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class ImageLoader {
    private HashMap<String, BufferedImage> listImage;

    public ImageLoader() {
        listImage = new HashMap<String, BufferedImage>();
        try {
            listImage.put("0", ImageIO.read(new File("src/main/assets/img/0.png")));
            listImage.put("1", ImageIO.read(new File("src/main/assets/img/1.png")));
            listImage.put("2", ImageIO.read(new File("src/main/assets/img/2.png")));
            listImage.put("3", ImageIO.read(new File("src/main/assets/img/3.png")));
            listImage.put("4", ImageIO.read(new File("src/main/assets/img/4.png")));
            listImage.put("5", ImageIO.read(new File("src/main/assets/img/5.png")));
            listImage.put("6", ImageIO.read(new File("src/main/assets/img/6.png")));
            listImage.put("7", ImageIO.read(new File("src/main/assets/img/7.png")));
            listImage.put("8", ImageIO.read(new File("src/main/assets/img/8.png")));
            listImage.put("mine", ImageIO.read(new File("src/main/assets/img/mine.png")));
            listImage.put("flag", ImageIO.read(new File("src/main/assets/img/flag.png")));
            listImage.put("exploded_mine", ImageIO.read(new File("src/main/assets/img/exploded_mine.png")));
            listImage.put("hidden", ImageIO.read(new File("src/main/assets/img/hidden.png")));
            listImage.put("timer", ImageIO.read(new File("src/main/assets/img/timer.png")));
            listImage.put("flag_icon", ImageIO.read(new File("src/main/assets/img/flag_icon.png")));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public HashMap<String, BufferedImage> getListImage() {
        return listImage;
    }
}
