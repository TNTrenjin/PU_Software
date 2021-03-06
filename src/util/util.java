package util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Dictionary;
import java.util.Hashtable;

public class util {

    public static Dictionary<String, BufferedImage> loadImages(String[] imageNameList) {
        Dictionary<String, BufferedImage> imageDict = new Hashtable<>();

        for (String imgName : imageNameList) {
            try {
                imageDict.put(imgName, ImageIO.read(new File("imgs/" + imgName + ".png")));
            } catch (IOException err) {
                System.out.println("Image load error: " + err.getMessage());
            }
        }

        return imageDict;
    }

    public static void drawAlignRightText(Graphics g, String text, int x, int y) {
        int fontOffset = (int) (g.getFont().getSize() * (7 / 12.0));
        g.drawString(text, x - text.length() * fontOffset, y);
    }

    public static void drawAlignCenterText(Graphics g, String text, int x, int y) {

        int cnt = 0;
        for (int i = 0; i < text.length(); i++)
            if (text.charAt(i) == ' ')
                cnt++;

        int fontOffset = (int) (g.getFont().getSize() * (7 / 12.0));
        g.drawString(text, x - (text.length() * fontOffset - cnt * fontOffset / 3) / 2, y);
    }
}
