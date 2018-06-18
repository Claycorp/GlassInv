package Claycorp;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;

public class DataLabel extends JComponent implements Printable
{

    //TODO: Link this to paper size.
    static int labelWidth = 696;
    static int labelHeight = 150;

    private Dimension labelSize = new Dimension(labelWidth, labelHeight);
    private BarcodeBuilder barcodeBuilder = new BarcodeBuilder();

    static boolean displayBarcode = true;
    static int xposBarcode = 10;
    static int yposBarcode = 10;
    static double barcodeScale = 3;
    static boolean barcodeHasBackground = true;
    static Color barcodeBackgroundColor = Color.WHITE;
    static Color barcodeColor = Color.BLACK;

    //TODO: We want floats for precise movements but the spinners are not working with them currently. Look into this someday perhaps...
    static String title = "Title";
    static boolean displayTitle = true;
    static float xposTitle = 50.5F;
    static float yposTitle = 50F;
    static String titleFont = "";
    static int titleFontSize = 20;
    static Color titleColor = Color.BLACK;

    static String size = "Size";
    static boolean displaySize = true;
    static float xposSize = 40F;
    static float yposSize = 20F;
    static String sizeFont = "";
    static int sizeFontSize = 20;
    static Color sizeColor = Color.BLACK;

    static String price = "Price";
    static boolean displayPrice = true;
    static float xposPrice = 30F;
    static float yposPrice = 10F;
    static String priceFont = "";
    static int priceFontSize = 20;
    static Color priceColor = Color.BLACK;

    //TODO: Link this to label properties
    private static BufferedImage label = new BufferedImage(696,150, BufferedImage.TYPE_INT_ARGB);

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
        buildLabel();
        g.drawImage(label, 0, 0, null);
    }

    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex)
    {
        if (pageIndex > 0)
        {
            //TODO: Properly logger this.
            System.out.print("No Printy");
            return NO_SUCH_PAGE;
        }
        Graphics2D g2d = (Graphics2D)graphics;
        g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
        System.out.print("\nX:" + pageFormat.getHeight()+" Y:" + pageFormat.getWidth());
        buildLabel();
        //TODO: Properly logger this.
        System.out.print("Printy");
        return PAGE_EXISTS;
    }

    public void buildLabel()
    {
        Graphics2D g2d = label.createGraphics();

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

    public static BufferedImage getLabel()
    {
        return label;
    }

}
