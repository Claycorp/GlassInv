package Claycorp;

import java.awt.*;
import java.awt.image.BufferedImage;

public class LabelBuilder
{
    int widith = 1;
    int hight = 1;

    BufferedImage label = new BufferedImage(widith, hight, BufferedImage.TYPE_BYTE_GRAY);
    Graphics2D g2D = label.createGraphics();


    public void buildLabel()
    {

        g2D.setPaint(Color.WHITE);
        g2D.fillRect(1,2,3,4);

        g2D.setPaint(Color.black);
        g2D.drawString("Test", 10,10);
        g2D.drawString("test2", 15,15);
    }
}
