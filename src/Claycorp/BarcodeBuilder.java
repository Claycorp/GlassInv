package Claycorp;

import uk.org.okapibarcode.backend.QrCode;
import uk.org.okapibarcode.output.Java2DRenderer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BarcodeBuilder
{

    private QrCode barcode = new QrCode();

    //Code128 barcode = new Code128();

    //TODO: Need a way to get all the data from the selected element in the table.
    //TODO: Do we need to save barcodes? Perhaps saving the label instead would be better.
    //TODO: Should I add support for other barcodes? Perhaps a sector to switch barcode types? (Would need some sort of validation as not all barcodes are alike....)
    public BarcodeBuilder()
    {
    }

    public void setBarcodeData(String codeData)
    {
        barcode.setContent(codeData);
    }

    public Image makeBarcode()
    {
        int widith = barcode.getWidth();
        int hight = barcode.getHeight();

        BufferedImage image = new BufferedImage(widith, hight, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = image.createGraphics();
        g2d.setPaint(Color.WHITE);
        g2d.fillRect(0,0, widith, hight);

        Java2DRenderer renderer = new Java2DRenderer(g2d, 1, Color.WHITE, Color.BLACK);
        renderer.render(barcode);
        return image;
    }

    public void saveBarcodeImage(String name)
    {
        int widith = barcode.getWidth();
        int hight = barcode.getHeight();

        BufferedImage image = new BufferedImage(widith, hight, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = image.createGraphics();
        g2d.setPaint(Color.WHITE);
        g2d.fillRect(0,0, widith, hight);

        Java2DRenderer renderer = new Java2DRenderer(g2d, 1, Color.WHITE, Color.BLACK);
        renderer.render(barcode);
        try
        {
            ImageIO.write(image, "png", new File(name +".png"));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public int getBarcodeHight()
    {
        return barcode.getHeight();
    }

    public int getBarcodeWidth()
    {
        return barcode.getWidth();
    }
}
