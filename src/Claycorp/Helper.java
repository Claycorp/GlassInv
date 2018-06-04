package Claycorp;


import javax.print.*;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.util.regex.Pattern;

public class Helper
{
    private static Pattern numberRegex = Pattern.compile("([0-9]+)");
    private static Pattern moneyRegex = Pattern.compile("(?:\\d*\\.)?\\d+");
    private static Font[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();

    public Helper() {}

    public static int regexNumberCheck(String in) throws NumberFormatException
    {
        if (numberRegex.matcher(in).matches())
        {
            return Integer.parseInt(in);
        }
        else
        {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "\'" + in + "\' is not a valid size!", "Number Error", JOptionPane.ERROR_MESSAGE);
            Logger.log("\'" + in + "\' is not a valid size!", 2);
            throw new NumberFormatException("\'" + in + "\' is not a valid size!");
        }
    }

    public static BigDecimal regexCompareMoney(String price) throws NumberFormatException
    {
        if (moneyRegex.matcher(price).matches())
        {
            return BigDecimal.valueOf(Long.parseLong(price));
        }
        else
        {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "\'" + price + "\' is not a valid price!", "Number Error", JOptionPane.ERROR_MESSAGE);
            Logger.log("\'" + price + "\' is not a valid price!", 2);
            throw new NumberFormatException("\'" + price + "\' is not a valid price!");
        }
    }

    public static BigDecimal calculateCostPerInch(BigDecimal pricePaid, int totalArea)
    {
        return pricePaid.divide(BigDecimal.valueOf(totalArea), 2, BigDecimal.ROUND_HALF_UP);
    }

    /** Returns an ImageIcon, or null if the path was invalid. */
    protected ImageIcon createImageIcon(String path, String description) {
        java.net.URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL, description);
        } else {
            Logger.log("Couldn't find file: " + path, 2);
            return null;
        }
    }

    public Font[] getFontList()
    {
        //TODO: Do something with this.
        return fonts;
    }

    public static void printLabel()
    {
        DataLabel labelData = new DataLabel();
        //Get the list of printers. We don't care about anything so null works.
        PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);

        //Need this to set the selected printer when opening the print UI.
        PrintService defaultPrintService = PrintServiceLookup.lookupDefaultPrintService();

        //All the magic settings for the magic box to do magic. This is empty and is filled in after print confirmation. (AKA Page settings.)
        PrintRequestAttributeSet attrib = new HashPrintRequestAttributeSet();

        //Resize?
        MediaPrintableArea mpa = new MediaPrintableArea(0.06F,0.06F,0.06F,0.06F,1);
        attrib.add(mpa);

        //Open the print UI.
        PrintService selectedPrintService = ServiceUI.printDialog(null, 150, 150, printServices, defaultPrintService, null, attrib);

        Doc doc = new SimpleDoc(labelData, DocFlavor.SERVICE_FORMATTED.PRINTABLE,null);

        if(selectedPrintService!=null)
        {
            DocPrintJob job = selectedPrintService.createPrintJob();
            try
            {
                job.print(doc, attrib);
            }
            catch (PrintException e)
            {
                e.printStackTrace();
            }
        }
        else
            System.out.println("selection cancelled");
    }
}
