package Claycorp;

import uk.org.okapibarcode.backend.QrCode;
import uk.org.okapibarcode.output.Java2DRenderer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BarcodeHelper
{
    QrCode barcode = new QrCode();
    //Code128 barcode = new Code128();


    //TODO: We need to build strings to pass to the barcode. Use another method to do this as it will be modifiable.

    public void setBarcode1()
    {
        barcode.setContent("Test");

        int widith = barcode.getWidth();
        int hight = barcode.getHeight();

        BufferedImage image = new BufferedImage(widith, hight, BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D g2d = image.createGraphics();
        g2d.setPaint(Color.WHITE);
        g2d.fillRect(0,0, widith, hight);

        Java2DRenderer renderer = new Java2DRenderer(g2d, 1, Color.WHITE, Color.BLACK);
        renderer.render(barcode);

        try
        {
            ImageIO.write(image, "png", new File("code128.png"));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        //this.barcode = barcode;
    }
}
