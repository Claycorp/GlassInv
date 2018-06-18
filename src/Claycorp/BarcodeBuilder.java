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
    {}

    public void setBarcodeData(String codeData)
    {
        barcode.setContent(codeData);
    }

    public Image makeBarcode()
    {
        // We need to do this dirty hack to get proper sizes when scaling. Why the lib does not handle it beats me.
        double acctualBarcodeSize = barcode.getHeight() * DataLabel.barcodeScale;
        // Because the scaling is done with a double we need to cast everything to an int or shit complains.
        BufferedImage image = new BufferedImage((int)acctualBarcodeSize, (int)acctualBarcodeSize, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();

        // Set the background color only if we really want it. Otherwise skip it.
        if (DataLabel.barcodeHasBackground)
        {
            // This sets the background color of the barcode.
            g2d.setPaint(DataLabel.barcodeBackgroundColor);
            // Again, Stupid scaling with doubles but we can't draw with doubles!
            g2d.fillRect(0, 0, (int) acctualBarcodeSize, (int) acctualBarcodeSize);
        }
        // NOTE: Color.White is the paper color, It DOES NOT change the rendering of the image at all! What purpose it serves exactly is unknown....
        Java2DRenderer renderer = new Java2DRenderer(g2d, DataLabel.barcodeScale, Color.WHITE, DataLabel.barcodeColor);
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
}
