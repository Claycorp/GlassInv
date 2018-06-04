package Claycorp;

import javax.swing.*;
import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;

public class DataLabel extends JComponent implements Printable
{

    static int labelWidth = 173;
    static int labelHeight = 58;

    private Dimension labelSize = new Dimension(labelWidth, labelHeight);
    private BarcodeBuilder barcodeBuilder = new BarcodeBuilder();

    static boolean displayBarcode = true;
    static int xposBarcode = 10;
    static int yposBarcode = 10;

    //TODO: We want floats for precise movements but the spinners are not working with them currently. Look into this someday perhaps...
    String title = "Title";
    static boolean displayTitle = true;
    static float xposTitle = 50.5F;
    static float yposTitle = 50F;
    static String titleFont = "";
    int titleFontSize = 20;
    static Color titleColor = Color.BLACK;

    String size = "Size";
    static boolean displaySize = true;
    static float xposSize = 40F;
    static float yposSize = 20F;
    static String sizeFont = "";
    int sizeFontSize = 20;
    static Color sizeColor = Color.BLACK;

    String price = "Price";
    static boolean displayPrice = true;
    static float xposPrice = 30F;
    static float yposPrice = 10F;
    static String priceFont = "";
    int priceFontSize = 20;
    static Color priceColor = Color.BLACK;


    public DataLabel()
    {}

    @Override
    public Dimension getMinimumSize() {
        return labelSize;
    }

    @Override
    public Dimension getPreferredSize() {
        return labelSize;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        buildLabel(g);
    }

    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException
    {
        if (pageIndex > 0)
        {
            //TODO: Properly logger this.
            System.out.print("No Printy");
            return NO_SUCH_PAGE;
        }
        Graphics2D g2d = (Graphics2D)graphics;
        //TODO: I think this is causing the oversized labels. Perhaps we should move print attributes here and attempt to get proper sizes?
        g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
        buildLabel(graphics);
        //TODO: Properly logger this.
        System.out.print("Printy");
        return PAGE_EXISTS;
    }

    public void buildLabel(Graphics graphics)
    {
        Graphics2D g2d = (Graphics2D) graphics;

        //Draws the background white
        g2d.setPaint(Color.WHITE);
        g2d.fillRect(0, 0, labelWidth, labelHeight);
        //Then we need to swap it to another color or they will be drawn in the same color as the background. (This took too long to realize)
        g2d.setPaint(Color.BLACK);
        if (displayTitle)
        {
            g2d.setFont(new Font(titleFont, Font.PLAIN, titleFontSize));
            g2d.setColor(titleColor);
            g2d.drawString(title, xposTitle, yposTitle);
        }

        if (displaySize)
        {
            g2d.setFont(new Font(sizeFont, Font.PLAIN, sizeFontSize));
            g2d.setColor(sizeColor);
            g2d.drawString(size, xposSize, yposSize);
        }

        if (displayPrice)
        {
            g2d.setFont(new Font(priceFont, Font.PLAIN, priceFontSize));
            g2d.setColor(priceColor);
            g2d.drawString(price, xposPrice, yposPrice);
        }

        if (displayBarcode)
        {
            barcodeBuilder.setBarcodeData("test");
            g2d.drawImage(barcodeBuilder.makeBarcode(), xposBarcode, yposBarcode, null);
        }

    }

}
