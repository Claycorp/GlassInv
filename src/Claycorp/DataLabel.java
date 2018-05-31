package Claycorp;

import javax.swing.*;
import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;

public class DataLabel extends JComponent implements Printable
{

    int labelWidth = 173;
    int labelHeight = 58;

    Dimension labelSize = new Dimension(labelWidth, labelHeight);
    BarcodeBuilder barcodeBuilder = new BarcodeBuilder();


    //TODO: Replace all width/height with font, color and font size.
    boolean displayBarcode = true;
    int xposBarcode = 10;
    int yposBarcode = 10;

    String title = "Title";
    boolean displayTitle = false;
    int xposTitle = 50;
    int yposTitle = 50;
    String titleFont = "";
    int titleFontSize = 20;
    int titleColor;

    String size = "Size";
    boolean displaySize = true;
    int xposSize = 40;
    int yposSize = 20;
    String sizeFont = "";
    int sizeFontSize = 20;
    Color sizeColor = new Color(0, 0, 0);

    String price = "Price";
    boolean displayPrice = true;
    int xposPrice = 30;
    int yposPrice = 10;
    String priceFont = "";
    int priceFontSize = 20;
    Color priceColor = new Color(0, 0, 0);


    public DataLabel()
    {
    }

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
        //TODO: Find out how to fix the paper size issues this has.
        //TODO: Apparently setting paper size here is too late? Try moving it.
        if (pageIndex > 0)
        {
            return NO_SUCH_PAGE;
        }
        Paper paper = pageFormat.getPaper();
        double width = 2.4d * 72d;
        double height = 1d * 72d;
        double margin = .1d * 72d;
        paper.setSize(width, height);
        paper.setImageableArea(margin, margin, width - margin, height - margin);
        pageFormat.setOrientation(PageFormat.PORTRAIT);
        pageFormat.setPaper(paper);
        //Graphics2D g2d = (Graphics2D)graphics;
        //g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
        System.out.print("Paper H "+paper.getHeight() + " -  W" + paper.getWidth() + " | ");
        System.out.print("Image H " +pageFormat.getImageableHeight() + " -  W" + pageFormat.getImageableWidth());
        buildLabel(graphics);
        return PAGE_EXISTS;
    }

    public void buildLabel(Graphics graphics)
    {
        //TODO: Find a way to get a list of fonts and add selector.
        //TODO: Add all Font modifiers to list and add selector.
        //TODO: Make font size option.
        //TODO: Make sure that each text element can have separate fonts.
        //TODO: Set up font color picking for each text element. (Not useful for B/W printers tho.)
        //TODO: Make a redraw method?
        Font font = new Font(titleFont, Font.BOLD, 30);
        Graphics2D g2d = (Graphics2D) graphics;

        //Draws the background white
        g2d.setPaint(Color.WHITE);
        g2d.fillRect(0, 0, labelWidth, labelHeight);
        //Then we need to swap it to another color or they will be drawn in the same color as the background. (This took too long to realize)
        g2d.setPaint(Color.red);
        if (displayTitle)
        {
            g2d.setFont(font);
            g2d.setColor(new Color(titleColor));
            //g2d.setPaint();
            System.out.print("Render Color: " + titleColor + " \n");
            g2d.drawString(title, xposTitle, yposTitle);
        }

        if (displaySize)
        {
            g2d.drawString(size, xposSize, yposSize);
        }

        if (displayPrice)
        {
            g2d.drawString(price, xposPrice, yposPrice);
        }

        if (displayBarcode)
        {
            barcodeBuilder.setBarcodeData("test");
            g2d.drawImage(barcodeBuilder.makeBarcode(), xposBarcode, yposBarcode, null);
        }
    }

    public void setTitleColor(Color in)
    {
        this.titleColor = in.getRGB();
        System.out.print("TitleColor@Set" + titleColor);
    }

}
