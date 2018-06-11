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
        // We need to do this dirty hack to get proper sizes when scaling. Why the lib dosen't handle this beats me.
        double acctualBarcodeSize = DataLabel.barcodeScale * barcode.getHeight();
        // QRcodes are always square, We only need one size to determine the correct width and height. Multiply by our dirty hack to get a proper size and tada, no missing barcode.
        int barcodeSize = barcode.getWidth() * (int) Math.round(acctualBarcodeSize);

        BufferedImage image = new BufferedImage(barcodeSize, barcodeSize, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = image.createGraphics();
        //TODO: Is this needed?
        //g2d.setPaint(Color.WHITE);
        g2d.fillRect(0,0, barcodeSize, barcodeSize);

        //TODO: Add color picker support to this.
        Java2DRenderer renderer = new Java2DRenderer(g2d, DataLabel.barcodeScale, Color.WHITE, Color.BLACK);
        renderer.render(barcode);
        return image;
    }

    //TODO: What will I ever do with this? Keep or toss? Could simplify into just writing to the file and use makeBarcode to draw it. Otherwise they could be merged somehow if need be.
    public void saveBarcodeImage(String name)
    {
        int widith = barcode.getWidth();
        int hight = barcode.getHeight();

        BufferedImage image = new BufferedImage(widith, hight, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = image.createGraphics();
        //TODO: Is this needed?
        //g2d.setPaint(Color.WHITE);
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
}
