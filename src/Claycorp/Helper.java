package Claycorp;

import javax.swing.*;
import java.awt.*;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
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
        PrinterJob printJob = PrinterJob.getPrinterJob();
        printJob.setPrintable(new DataLabel());
        if (printJob.printDialog())
        {
            try
            {
                printJob.print();
            }
            catch (PrinterException pe)
            {
                //TODO: Log, Error, Dialog and all that magic it here.
            }
        }
    }

}
